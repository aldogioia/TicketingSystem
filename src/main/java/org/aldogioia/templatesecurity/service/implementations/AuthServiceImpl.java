package org.aldogioia.templatesecurity.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dao.UserDao;
import org.aldogioia.templatesecurity.data.dto.responses.RefreshResponseDto;
import org.aldogioia.templatesecurity.data.dto.responses.SignInResponseDto;
import org.aldogioia.templatesecurity.data.dto.creates.CustomerCreateDto;
import org.aldogioia.templatesecurity.data.entities.User;
import org.aldogioia.templatesecurity.data.enumerators.TokenType;
import org.aldogioia.templatesecurity.security.authentication.JwtHandler;
import org.aldogioia.templatesecurity.security.exception.customException.TokenException;
import org.aldogioia.templatesecurity.service.interfaces.AuthService;
import org.aldogioia.templatesecurity.service.interfaces.BlacklistService;
import org.aldogioia.templatesecurity.service.interfaces.CustomerService;
import org.aldogioia.templatesecurity.service.interfaces.PasswordCodeService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserDao userDao;
    private final JwtHandler jwtHandler;
    private final CustomerService customerService;
    private final BlacklistService blacklistService;
    private final AuthenticationManager authenticationManager;
    private final PasswordCodeService passwordCodeService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SignInResponseDto signIn(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

        SignInResponseDto authResponseDto = new SignInResponseDto();
        authResponseDto.setAccessToken(jwtHandler.generateAccessToken(user));
        authResponseDto.setRefreshToken(jwtHandler.generateRefreshToken(user));

        return authResponseDto;
    }

    @Override
    public void signUp(CustomerCreateDto customerCreateDto) {
        customerService.createCustomer(customerCreateDto);
    }

    @Override
    public void updateRequest(String email) {
        passwordCodeService.createCode(email);
    }

    @Override
    public void updatePassword(String email, String password, String code) {
        if (!passwordCodeService.validateCode(email, code)) {
            throw new TokenException("Codice di verifica non valido o scaduto");
        }

        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));

        user.setPassword(passwordEncoder.encode(password));
        userDao.save(user);
    }

    @Override
    public RefreshResponseDto refresh(HttpServletRequest request) {
        String refreshToken = jwtHandler.getJwtFromRequest(request, TokenType.REFRESH);

        if (blacklistService.isTokenBlacklisted(refreshToken)) {
            throw new TokenException("Refresh token fornito già revocato");
        }

        if (!jwtHandler.isValidRefreshToken(refreshToken)) {
            throw new TokenException("Refresh token non valido o scaduto");
        }

        try {
            String email = jwtHandler.getEmailFromToken(refreshToken);
            var user = userDao.findByEmail(email)
                    .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));

            RefreshResponseDto refreshResponseDto = new RefreshResponseDto();
            refreshResponseDto.setAccessToken(jwtHandler.generateAccessToken(user));
            return refreshResponseDto;
        }
        catch (Exception e) {
            throw new TokenException("Errore durante il refresh del token");
        }
    }

    @Override
    public void signOut(HttpServletRequest request) {
        blacklistService.addTokenToBlacklist(jwtHandler.getAccessToken(request));
        blacklistService.addTokenToBlacklist(jwtHandler.getRefreshToken(request));
    }
}
