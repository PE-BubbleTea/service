package ro.unibuc.hello.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ro.unibuc.hello.data.StatisticEntity;
import ro.unibuc.hello.data.StatisticRepository;
import ro.unibuc.hello.dto.Currency;
import ro.unibuc.hello.dto.Statistic;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.Map;

import static org.mockito.Mockito.when;

@SpringBootTest
@Tag("IT")
class ApplicationServiceTestIT {

    @Autowired
    StatisticRepository statisticRepository;

//    @MockBean
//    StatisticRepository mockStatisticRepository;

    @Autowired
    ApplicationService applicationService;

   /* @Test
    void test_buildStatisticFromEurInfo_returnsStatisticWithEurInfo() {
        // Arrange
        String title = "Conversion_2022-03-17_EUR";

        // Act
        Statistic statistic = applicationService.buildStatisticFromInfo(title);

        // Assert
        Assertions.assertEquals("RON to EUR", statistic.getDescription());
        Assertions.assertEquals((float) 0.2, statistic.getStatistic());
    }
*/
//    @Test
//    void test_buildStatisticFromUsdInfo_returnsStatisticWithUsdInfo() {
//        // Arrange
//        String title = "Conversion_2022-03-23_USD";
//
//        StatisticEntity statisticEntity = new StatisticEntity(title, "RON to USD", (float) 0.21);
//
//        when(mockStatisticRepository.findByTitle(title)).thenReturn(statisticEntity);
//
//        // Act
//        Statistic statistic = applicationService.buildStatisticFromInfo(title);
//
//        // Assert
//        Assertions.assertEquals("RON to USD", statistic.getDescription());
//        Assertions.assertEquals((float) 0.0, statistic.getStatistic());
//    }
//
    @Test
    void test_buildWeeklyStatisticFromInfo_returnsWeeklyStatisticWithInfo() {
        // Arrange
        String currency = "USD";

        // Act
        Statistic statistic = applicationService.getWeeklyUpdate(currency);

        // Assert
        Assertions.assertEquals("RON to USD", statistic.getTitle());
        Assertions.assertEquals("Weekly statistic", statistic.getDescription());
        Assertions.assertEquals((float) 0.0, statistic.getStatistic());
    }

    @Test
    void test_buildDailyStatisticFromInfo_returnsDailyStatisticWithInfo() {
        // Arrange
        String currency = "EUR";

        // Act
        Statistic statistic = applicationService.getDailyUpdate(currency);

        // Assert
        Assertions.assertEquals("RON to EUR", statistic.getTitle());
        Assertions.assertEquals("Daily statistic", statistic.getDescription());
        Assertions.assertEquals((float) 0.0, statistic.getStatistic());
    }

   /* @Test
    void test_updateApiData_returnsCurrency() {
        // Arrange
//        String currency = "EUR";

        // Act
        Currency currency = applicationService.updateApiData();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Double> rates = objectMapper.convertValue(currency.getRates(), Map.class);
        // Assert
        Assertions.assertEquals("success", currency.getResult());
        Assertions.assertTrue(rates.containsKey("EUR"));
        Assertions.assertTrue(rates.containsKey("USD"));
        Assertions.assertTrue(rates.containsKey("CHF"));

        Assertions.assertNotNull(rates.get("EUR"));
        Assertions.assertNotNull(rates.get("USD"));
        Assertions.assertNotNull(rates.get("CHF"));
    }*/
}
