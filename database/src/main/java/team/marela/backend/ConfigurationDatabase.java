package team.marela.backend;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"team.marela.backend.database.repositories"})
@EntityScan(basePackages = {"team.marela.backend.database.entities"})
public class ConfigurationDatabase {
}
