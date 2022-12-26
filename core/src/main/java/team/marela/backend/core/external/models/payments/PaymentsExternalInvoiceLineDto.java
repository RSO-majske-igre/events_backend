package team.marela.backend.core.external.models.payments;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentsExternalInvoiceLineDto {

    private UUID id;
    private String name;
    private Long price;
}
