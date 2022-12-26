package team.marela.backend.core.external.models.payments;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
