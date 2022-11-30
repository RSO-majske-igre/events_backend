package team.marela.backend.core.models.participants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.marela.backend.core.validators.NotNullUUIDValidationGroup;
import team.marela.backend.database.entities.participants.ParticipantEntity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DormDto {
    @NotNull(groups = NotNullUUIDValidationGroup.class)
    UUID id;

    @NotEmpty
    private String dormName;

    @JsonIgnoreProperties(value = {
            "dorm",
            "entries"
    })
    private Set<ParticipantEntity> participants;
}
