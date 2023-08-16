package com.bbrick.auth.core.auth.domain;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
    private final Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    public String encode(String rawPasword) {
        return this.encoder.encode(rawPasword);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return this.encoder.matches(rawPassword, encodedPassword);
    }
}
