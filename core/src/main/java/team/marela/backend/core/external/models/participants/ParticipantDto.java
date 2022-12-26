package team.marela.backend.core.external.models.participants;

import lombok.*;
import team.marela.backend.core.validators.NotNullUUIDValidationGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDto {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private UUID dormId;
    private String dormName;
    private UUID addressId;
    private String addressLine1;
    private String addressLine2;
    private String addressCity;
    private String addressPostalCode;
}
