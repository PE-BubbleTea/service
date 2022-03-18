package ro.unibuc.hello;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ro.unibuc.hello.data.StatisticRepository;

@SpringBootTest
class HelloApplicationTests {

	@MockBean
	StatisticRepository mockRepository;

	@Test
	void contextLoads() {
	}

}
