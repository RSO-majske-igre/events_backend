package team.marela.backend.core.models.participants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.marela.backend.core.validators.NotNullUUIDValidationGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDto {
    @NotNull(groups = NotNullUUIDValidationGroup.class)
    private UUID id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String phone;

    @NotNull
    @JsonIgnoreProperties(value = {
            "participants"
    })
    private DormDto dorm;

//    private Set<EntryEntity> entries;
}
