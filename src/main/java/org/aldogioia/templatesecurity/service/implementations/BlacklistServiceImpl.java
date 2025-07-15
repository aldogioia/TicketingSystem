package org.aldogioia.templatesecurity.service.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dao.BlacklistDao;
import org.aldogioia.templatesecurity.data.entities.Blacklist;
import org.aldogioia.templatesecurity.security.authentication.JwtHandler;
import org.aldogioia.templatesecurity.service.interfaces.BlacklistService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class BlacklistServiceImpl implements BlacklistService {
    private final BlacklistDao blacklistDao;

    @Override
    public void addTokenToBlacklist(String token) {
        Blacklist blacklist = new Blacklist();
        blacklist.setExpiration(JwtHandler.getExpirationTime(token));
        blacklist.setToken(token);

        blacklistDao.save(blacklist);
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return blacklistDao.existsByToken(token);
    }

    @Scheduled(cron = "0 0 15 * * *")
    @Transactional
    public void cleanUp() {
        blacklistDao.deleteAllByExpirationBefore(Date.from(Instant.now().truncatedTo(ChronoUnit.SECONDS)));
    }
}
