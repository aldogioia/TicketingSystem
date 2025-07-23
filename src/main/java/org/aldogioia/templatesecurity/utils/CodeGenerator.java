package org.aldogioia.templatesecurity.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class CodeGenerator {
    private static final SecureRandom random = new SecureRandom();

    public static String generateCode() {
        int code = 10000 + random.nextInt(90000); // da 10000 a 99999
        return String.valueOf(code);
    }
}
