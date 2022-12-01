package team.marela.backend.core.models.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.marela.backend.core.validators.NotNullUUIDValidationGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    @NotNull(groups = NotNullUUIDValidationGroup.class)
    UUID id;

    @NotNull
    @NotEmpty
    private String eventName;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private BigDecimal fee;
    @NotNull
    @NotEmpty
    private String teamDescription;
    private String imageUrl;

    private UUID locationId;
    @NotNull
    @NotEmpty
    private String locationName;
    private String locationUrl;
    private String geoLat;
    private String geoLon;

    private Set<EntryDto> entries;

    private Set<EventResultDto> results;
}
