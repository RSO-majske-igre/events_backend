package team.marela.backend.core.external.models.payments;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
