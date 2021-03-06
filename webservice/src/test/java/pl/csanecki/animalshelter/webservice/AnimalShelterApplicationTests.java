package pl.csanecki.animalshelter.webservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.csanecki.animalshelter.webservice.config.ShelterAppConfig;

import javax.sql.DataSource;

@SpringBootTest(classes = AnimalShelterApplicationTests.Config.class)
@ActiveProfiles("container")
@Testcontainers
class AnimalShelterApplicationTests {

	@Container
	private static final MySQLContainer DB_CONTAINER = new MySQLContainer()
			.withUsername("test")
			.withPassword("test");

	static {
		DB_CONTAINER.start();
		System.setProperty("DB_PORT", String.valueOf(DB_CONTAINER.getFirstMappedPort()));
	}

	@Test
	void contextLoads() { }

	@Configuration(proxyBeanMethods = false)
	@EnableAutoConfiguration
	static class Config extends ShelterAppConfig {

		@Bean
		DataSource dataSource() {
			return DataSourceBuilder.create()
					.url(DB_CONTAINER.getJdbcUrl())
					.username(DB_CONTAINER.getUsername())
					.password(DB_CONTAINER.getPassword())
					.build();
		}
	}
}

