package team.marela.backend.core.health;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ExternalHealthIndicator {
    private static final RestTemplate restTemplate = new RestTemplate();
    private final CircuitBreakerFactory circuitBreakerFactory;

    protected Health health(String url) {
        return circuitBreakerFactory.create(ExternalHealthIndicator.class.getSimpleName()).run(
                () -> circuitBreakerRun(url),
                throwable -> Health.up().build()
        );
    }

    private Health circuitBreakerRun(String url) {
        var respMap = restTemplate
                .getForEntity(String.format("%s/actuator/health", url), Map.class)
                .getBody();

        if(respMap == null) {
            return Health.down().build();
        }

        return respMap.get("status").equals("UP")
                ? Health.up().build()
                : Health.down().build();
    }
}
