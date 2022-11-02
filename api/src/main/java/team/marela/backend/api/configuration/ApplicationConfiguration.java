package team.marela.backend.api.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import team.marela.backend.ConfigurationDatabase;
import team.marela.backend.core.ConfigurationServices;

@Configuration
@Import(value = {ConfigurationDatabase.class, ConfigurationServices.class})
public class ApplicationConfiguration {
}
