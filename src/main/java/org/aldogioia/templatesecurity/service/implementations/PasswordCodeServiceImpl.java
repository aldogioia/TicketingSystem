package org.aldogioia.templatesecurity.service.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dao.PasswordCodeDao;
import org.aldogioia.templatesecurity.data.entities.PasswordCode;
import org.aldogioia.templatesecurity.service.interfaces.EmailService;
import org.aldogioia.templatesecurity.service.interfaces.PasswordCodeService;
import org.aldogioia.templatesecurity.utils.CodeGenerator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordCodeServiceImpl implements PasswordCodeService {
    private final PasswordCodeDao passwordCodeDao;
    private final EmailService emailService;

    @Override
    public void createCode(String email) {
        String code = CodeGenerator.generateCode();
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);


        PasswordCode passwordCode = new PasswordCode();
        passwordCode.setEmail(email);
        passwordCode.setCode(code);
        passwordCode.setExpiresAt(LocalDateTime.ofInstant(
                issuedAt.plus(10, ChronoUnit.MINUTES),
                java.time.ZoneId.systemDefault())
        );

        passwordCodeDao.save(passwordCode);

        try {
            emailService.sendRecoveryPasswordCode(email, code);
        } catch (Exception e) {
            passwordCodeDao.delete(passwordCode);
            throw new RuntimeException("Errore nell'invio dell'email col codice di recupero.");
        }
    }

    @Override
    public boolean validateCode(String email, String code) {
        Optional<PasswordCode> passwordCodeOptional = passwordCodeDao.findByEmailAndCode(email, code);
        if (passwordCodeOptional.isPresent()) {
            passwordCodeDao.delete(passwordCodeOptional.get());
            return true;
        }
        return false;
    }

    @Transactional
    @Scheduled(cron = "0 0 9 * * MON")
    public void cleanUp() {
        passwordCodeDao.deleteAllByExpiresAtIsBefore(LocalDateTime.now());
    }
}
