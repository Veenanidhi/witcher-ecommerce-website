package com.witcher.e_commerce.application.witcher.service;

import com.witcher.e_commerce.application.witcher.dao.VerificationTokenRepository;
import com.witcher.e_commerce.application.witcher.entity.VerificationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final VerificationTokenRepository verificationTokenRepository;

    public VerificationToken verificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (Objects.nonNull(verificationToken)) {
            return verificationToken;
        }
        return null;
    }
}
