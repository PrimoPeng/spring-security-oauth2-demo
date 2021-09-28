package com.feng;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {

    @Test
    public void password() {
        // 每次打印的结果都不一样，不影响
        System.out.println(new BCryptPasswordEncoder().encode("oauth2"));
    }

}