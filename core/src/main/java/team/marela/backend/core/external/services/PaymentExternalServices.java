package team.marela.backend.core.external.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import team.marela.backend.core.external.models.payments.PaymentsExternalInvoiceDto;
import team.marela.backend.core.external.models.payments.PaymentsExternalInvoiceLineDto;
import team.marela.backend.core.external.models.payments.PaymentsExternalInvoiceStatusEnum;
import team.marela.backend.database.entities.ParticipantEntity;
import team.marela.backend.database.entities.entries.EntryEntity;

import java.math.BigDecimal;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PaymentExternalServices {
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${external.url.majskeigre_payments_url}")
    private String paymentsBaseUrl;


    public PaymentsExternalInvoiceDto createInvoice(EntryEntity entry, ParticipantEntity participant) {
        var price = entry.getEvent().getFee();
        var wholePart = price.divideToIntegralValue(BigDecimal.ONE).toBigInteger();
        var fractionPart = price.remainder(BigDecimal.ONE).multiply(new BigDecimal(100)).toBigInteger();
        var stringPrice =
                String.format(
                        "%s%s",
                        wholePart,
                        fractionPart
                );
        var longPrice = Long.parseLong(stringPrice);

        var invoice = PaymentsExternalInvoiceDto.builder()
                .participantId(participant.getParticipantId())
                .sumAmount(longPrice)
                .status(PaymentsExternalInvoiceStatusEnum.CREATED)
                .invoiceLines(
                        Set.of(
                                PaymentsExternalInvoiceLineDto.builder()
                                        .name(entry.getEvent().getEventName())
                                        .price(longPrice)
                                        .build()
                        )
                ).build();

        var x = restTemplate.postForEntity(
                String.format("%s/invoice", paymentsBaseUrl),
                invoice,
                PaymentsExternalInvoiceDto.class
        ).getBody();

        return x;
    }
}
