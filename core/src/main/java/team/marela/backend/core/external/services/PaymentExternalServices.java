package team.marela.backend.core.external.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import team.marela.backend.core.external.models.participants.ParticipantsExternalParticipantDto;
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


    public void createInvoice(EntryEntity entry, ParticipantEntity participant) {
        var price = entry.getEvent().getFee();
        var longPrice = Long.parseLong(
                String.format(
                        "%s%s",
                        price.divideToIntegralValue(BigDecimal.ONE),
                        price.remainder(BigDecimal.ONE)
                )
        );

        var invoice = PaymentsExternalInvoiceDto.builder()
                .customerId(participant.getParticipantId())
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
                String.format("%s/participants", paymentsBaseUrl),
                invoice,
                PaymentsExternalInvoiceDto.class
        ).getBody();

        x = x;
    }
}
