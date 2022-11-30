package team.marela.backend.core.models.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.marela.backend.core.validators.NotNullUUIDValidationGroup;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventResultDto {

    @NotNull(groups = NotNullUUIDValidationGroup.class)
    UUID id;

    @NotNull
    @Min(1)
    private Integer place;
    @NotNull
    private Integer points;
    @NotNull
    private UUID eventId;

    @NotNull
    @JsonIgnoreProperties(value = {
            "place", "points", "eventId"
    })
    private EntryDto entry;
}
