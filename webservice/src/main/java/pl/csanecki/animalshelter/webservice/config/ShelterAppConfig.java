package pl.csanecki.animalshelter.webservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.csanecki.animalshelter.domain.service.ShelterRepository;
import pl.csanecki.animalshelter.webservice.repository.ShelterJdbcRepository;
import pl.csanecki.animalshelter.webservice.write.ShelterJdbcWriteRepository;
import pl.csanecki.animalshelter.webservice.write.ShelterWriteRepository;

@Configuration(proxyBeanMethods = false)
public class ShelterAppConfig {

    @Bean
    public ShelterRepository shelterRepository(JdbcTemplate jdbcTemplate) {
        return new ShelterJdbcRepository(jdbcTemplate);
    }

    @Bean
    public ShelterWriteRepository shelterWriteRepository(JdbcTemplate jdbcTemplate) {
        return new ShelterJdbcWriteRepository(jdbcTemplate);
    }
}
