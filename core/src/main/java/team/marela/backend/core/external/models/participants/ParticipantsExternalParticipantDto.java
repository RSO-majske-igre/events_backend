package team.marela.backend.core.external.models.participants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.marela.backend.core.models.EntryDto;
import team.marela.backend.core.validators.NotNullUUIDValidationGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantsExternalParticipantDto {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private ParticipantsExternalDormDto dorm;
    private Set<EntryDto> entries;
}
