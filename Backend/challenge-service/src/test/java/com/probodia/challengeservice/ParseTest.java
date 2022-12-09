package com.probodia.challengeservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@Slf4j
public class ParseTest {

    @Test
    void testDate() throws ParseException {
        String stdate = "2022-10-09";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = formatter.parse(stdate);
        Date curDate = new Date();

        if(curDate.after(startDate)){
            log.info("여기 들어와야 함");
        }
        else{
            log.info("반대로");
        }

    }

}
