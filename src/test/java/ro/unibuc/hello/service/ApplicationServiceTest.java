package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.data.StatisticRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.Statistic;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.text.DecimalFormat;

import static org.mockito.Mockito.when;

@SpringBootTest
public class ApplicationServiceTest {
    @Mock
    StatisticRepository mockStatisticRepository;

    @InjectMocks
    ApplicationService applicationService = new ApplicationService();

    @Test
    void test_hello_returnsGreeting(){
        // Arrange
        String name = "John";

        // Act
        Greeting greeting = applicationService.sayHello(name);

        // Assert
        Assertions.assertEquals(1, greeting.getId());
        Assertions.assertEquals("Hello, John!", greeting.getContent());
    }

    @Test
    void test_weekly_statistic_returns_statistic_usd() {
        // Arrange
        String currency = "USD";

        // Act
        Statistic statistic = applicationService.getWeeklyUpdate(currency);

        DecimalFormat df = new DecimalFormat("#.###");

        // Assert
        Assertions.assertEquals("RON to USD", statistic.getTitle());
        Assertions.assertEquals("Weekly statistic", statistic.getDescription());
        Assertions.assertEquals("0.21", df.format(statistic.getStatistic()));
    }

    @Test
    void test_weekly_statistic_returns_statistic_eur() {
        // Arrange
        String currency = "EUR";

        // Act
        Statistic statistic = applicationService.getWeeklyUpdate(currency);

        DecimalFormat df = new DecimalFormat("#.###");

        // Assert
        Assertions.assertEquals("RON to EUR", statistic.getTitle());
        Assertions.assertEquals("Weekly statistic", statistic.getDescription());
        Assertions.assertEquals("0.20", df.format(statistic.getStatistic()));
    }

    @Test
    void test_daily_statistic_returns_statistic_usd() {
        // Arrange
        String currency = "USD";

        // Act
        Statistic statistic = applicationService.getDailyUpdate(currency);

        DecimalFormat df = new DecimalFormat("#.###");

        // Assert
        Assertions.assertEquals("RON to USD", statistic.getTitle());
        Assertions.assertEquals("Daily statistic", statistic.getDescription());
        Assertions.assertEquals("0.21", df.format(statistic.getStatistic()));
    }

    @Test
    void test_daily_statistic_returns_statistic_eur() {
        // Arrange
        String currency = "EUR";

        // Act
        Statistic statistic = applicationService.getDailyUpdate(currency);

        DecimalFormat df = new DecimalFormat("#.###");

        // Assert
        Assertions.assertEquals("RON to EUR", statistic.getTitle());
        Assertions.assertEquals("Daily statistic", statistic.getDescription());
        Assertions.assertEquals("0.20", df.format(statistic.getStatistic()));
    }
}
