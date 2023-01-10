package team.marela.backend.core.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.marela.backend.core.external.models.participants.ParticipantDto;
import team.marela.backend.core.validators.NotNullUUIDValidationGroup;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntryDto {

    @NotNull(groups = NotNullUUIDValidationGroup.class)
    UUID id;

    @NotNull
    private Boolean paid;
    @NotNull
    private Boolean accepted;
    @NotNull
    private UUID eventId;

    private Set<ParticipantDto> participants;

    private Set<UUID> invoiceIds;

    private Integer place;
    private Integer points;
}
