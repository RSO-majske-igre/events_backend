package team.marela.backend.core.external.models.payments;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentsExternalInvoiceDto {
    private UUID id;
    private Long sumAmount;
    private UUID customerId;
    private PaymentsExternalInvoiceStatusEnum status;
    private Set<PaymentsExternalInvoiceLineDto> invoiceLines;
}
