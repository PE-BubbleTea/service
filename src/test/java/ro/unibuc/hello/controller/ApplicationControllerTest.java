package ro.unibuc.hello.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.Statistic;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.ApplicationService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class ApplicationControllerTest {
    @Mock
    private ApplicationService applicationService;

    @InjectMocks
    private HelloWorldController helloWorldController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void test_sayHello() throws Exception {
        // Arrange
        Greeting greeting = new Greeting(1, "Hello, there!");

        when(applicationService.sayHello(any())).thenReturn(greeting);

        // Act
        MvcResult result = mockMvc.perform(get("/hello-world?name=there")
                        .content(objectMapper.writeValueAsString(greeting))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(greeting));
    }

    @Test
    void test_weekly_update() throws Exception {
        // Arrange
        Statistic statistic = new Statistic("RON to EUR", "Weekly statistic", (float) 0.20);

        when(applicationService.getWeeklyUpdate(any())).thenReturn(statistic);

        // Act
        MvcResult result = mockMvc.perform(get("/weekly-update?currency=EUR")
                        .content(objectMapper.writeValueAsString(statistic))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(statistic));
    }

    @Test
    void test_daily_update() throws Exception {
        // Arrange
        Statistic statistic = new Statistic("RON to EUR", "Daily statistic", (float) 0.20);

        when(applicationService.getWeeklyUpdate(any())).thenReturn(statistic);

        // Act
        MvcResult result = mockMvc.perform(get("/daily-update?currency=EUR")
                        .content(objectMapper.writeValueAsString(statistic))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(statistic));
    }


}