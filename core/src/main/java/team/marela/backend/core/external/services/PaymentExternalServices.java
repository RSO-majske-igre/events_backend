package team.marela.backend.core.external.services;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import team.marela.backend.core.external.models.payments.PaymentsExternalInvoiceDto;
import team.marela.backend.core.external.models.payments.PaymentsExternalInvoiceLineDto;
import team.marela.backend.core.external.models.payments.PaymentsExternalInvoiceStatusEnum;
import team.marela.backend.database.entities.ParticipantEntity;
import team.marela.backend.database.entities.entries.EntryEntity;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Log
@Service
@RequiredArgsConstructor
public class PaymentExternalServices {
    private final RestTemplate restTemplate = new RestTemplate();
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final MeterRegistry meterRegistry;

    @Value("${external.url.majskeigre_payments_url}")
    private String paymentsBaseUrl;

    private Counter invoiceCreationSuccessCounter;
    private Counter invoiceCreationFailureCounter;

    @PostConstruct
    public void postConstruct() {
        invoiceCreationSuccessCounter = Counter
                .builder("external__payments__invoiceCreation_success")
                .description("Number of successfully created invoice creation requests")
                .register(meterRegistry);
        invoiceCreationFailureCounter = Counter
                .builder("external__payments__invoiceCreation_failure")
                .description("Number of failures in invoice creation requests")
                .register(meterRegistry);
    }


    @Async
    @Timed(value = "external__payments__invoiceCreation_requestTime", description = "Time to process invoice creation", percentiles = {0.25, 0.95})
    public CompletableFuture<PaymentsExternalInvoiceDto> createInvoice(EntryEntity entry, ParticipantEntity participant) {
        return CompletableFuture.completedFuture(
                circuitBreakerFactory.create("invoiceGeneratingCircuitBreaker").run(
                        () -> createInvoiceCircuitBreakerRunnable(entry, participant),
                        (throwable) -> createInvoiceCircuitBreakerFallback()
                )
        );
    }

    private PaymentsExternalInvoiceDto createInvoiceCircuitBreakerRunnable(EntryEntity entry, ParticipantEntity participant) {
        var res = restTemplate.postForEntity(
                String.format("%s/invoice", paymentsBaseUrl),
                createInvoiceData(entry, participant),
                PaymentsExternalInvoiceDto.class
        ).getBody();

        log.info("Invoice creation success");
        invoiceCreationSuccessCounter.increment(1);
        return res;
    }

    private PaymentsExternalInvoiceDto createInvoiceCircuitBreakerFallback() {
        log.severe("Invoice creation failure");
        invoiceCreationFailureCounter.increment(1);
        return null;
    }

    private PaymentsExternalInvoiceDto createInvoiceData(EntryEntity entry, ParticipantEntity participant) {
        var stripePriceFormat = generateStripeFormattedPrice(entry.getEvent().getFee());

        return PaymentsExternalInvoiceDto.builder()
                .participantId(participant.getParticipantId())
                .entryId(entry.getId())
                .sumAmount(stripePriceFormat)
                .status(PaymentsExternalInvoiceStatusEnum.CREATED)
                .invoiceLines(
                        Set.of(
                                PaymentsExternalInvoiceLineDto.builder()
                                        .name(entry.getEvent().getEventName())
                                        .price(stripePriceFormat)
                                        .build()
                        )
                ).build();
    }

    private Long generateStripeFormattedPrice(BigDecimal price) {
        var wholePart = price.divideToIntegralValue(BigDecimal.ONE).toBigInteger();
        var fractionPart = price.remainder(BigDecimal.ONE).multiply(new BigDecimal(100)).toBigInteger();
        var stringPrice =
                String.format(
                        "%s%s",
                        wholePart,
                        fractionPart
                );

        return Long.parseLong(stringPrice);
    }
}
