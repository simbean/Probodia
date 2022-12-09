package com.probodia.challengeservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Pattern;

@SpringBootTest
class ChallengeserviceApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    void testReg(){
        String pattern = "^[0-9]*$"; // 숫자만 등장하는지
        String str = "123321";

        boolean result = Pattern.matches(pattern, str);
        System.out.println(result); // true
    }
}
