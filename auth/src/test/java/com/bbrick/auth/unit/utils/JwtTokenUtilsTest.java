package com.bbrick.auth.unit.utils;

import com.bbrick.auth.comn.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Slf4j
@DisplayName("JwtTokenUtil 클래스")
public class JwtTokenUtilsTest {
    @DisplayName("check 메서드는")
    @Nested
    class check {

        @Test
        void context() {
            JwtTokenUtil utils = new JwtTokenUtil();
            utils.getSignKey();
        }
    }
}
