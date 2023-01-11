package team.marela.backend.core.external.services;

import lombok.RequiredArgsConstructor;
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

import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class PaymentExternalServices {
    private final RestTemplate restTemplate = new RestTemplate();
    private final CircuitBreakerFactory circuitBreakerFactory;

    @Value("${external.url.majskeigre_payments_url}")
    private String paymentsBaseUrl;


    @Async
    public CompletableFuture<PaymentsExternalInvoiceDto> createInvoice(EntryEntity entry, ParticipantEntity participant) {
        return CompletableFuture.completedFuture(
                circuitBreakerFactory.create("invoiceGeneratingCircuitBreaker").run(
                       () ->  restTemplate.postForEntity(
                               String.format("%s/invoice", paymentsBaseUrl),
                               createInvoiceData(entry, participant),
                               PaymentsExternalInvoiceDto.class
                       ).getBody(),
                       (throwable) -> null
               )
        );
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
