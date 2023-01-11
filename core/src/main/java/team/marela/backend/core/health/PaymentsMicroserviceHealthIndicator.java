package team.marela.backend.core.health;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;

@Component
public class PaymentsMicroserviceHealthIndicator extends ExternalHealthIndicator implements HealthIndicator {

    @Value("${external.url.majskeigre_payments_url}")
    private String baseUrl;

    public PaymentsMicroserviceHealthIndicator(CircuitBreakerFactory circuitBreakerFactory) {
        super(circuitBreakerFactory);
    }

    @Override
    public Health health() {
        return super.health(baseUrl);
    }
}
