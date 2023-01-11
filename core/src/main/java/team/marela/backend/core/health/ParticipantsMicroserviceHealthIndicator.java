package team.marela.backend.core.health;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;

@Component
public class ParticipantsMicroserviceHealthIndicator extends ExternalHealthIndicator implements HealthIndicator {

    @Value("${external.url.majskeigre_participants_url}")
    private String baseUrl;

    public ParticipantsMicroserviceHealthIndicator(CircuitBreakerFactory circuitBreakerFactory) {
        super(circuitBreakerFactory);
    }

    @Override
    public Health health() {
        return super.health(baseUrl);
    }
}
