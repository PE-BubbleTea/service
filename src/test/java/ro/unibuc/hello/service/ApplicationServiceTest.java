package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.exception.EntityNotFoundException;

import static org.mockito.Mockito.when;

@SpringBootTest
public class ApplicationServiceTest {
    @Mock
    InformationRepository mockInformationRepository;

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
}
