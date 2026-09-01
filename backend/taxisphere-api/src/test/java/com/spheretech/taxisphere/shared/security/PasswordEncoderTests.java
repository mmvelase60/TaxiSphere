package com.spheretech.taxisphere.shared.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class PasswordEncoderTests {

    @Test
    void bcryptEncoderMatchesRawPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String encoded = encoder.encode("change-me");

        assertThat(encoded).isNotEqualTo("change-me");
        assertThat(encoder.matches("change-me", encoded)).isTrue();
    }
}
