package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.dto.Statistic;

import java.text.DecimalFormat;

@SpringBootTest
public class StatisticTest {
    Statistic myStatistic = new Statistic("RON to EUR", "Weekly statistic", (float) 0.20);

    @Test
    void test_title(){
        Assertions.assertSame("RON to EUR", myStatistic.getTitle());
    }

    @Test
    void test_description(){
        Assertions.assertEquals("Weekly statistic", myStatistic.getDescription());
    }

    @Test
    void test_statistic(){
        DecimalFormat df = new DecimalFormat("#.##");

        Assertions.assertEquals("0.2", df.format(myStatistic.getStatistic()));
    }
}
