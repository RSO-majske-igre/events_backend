package team.marela.backend.core.external.models.participants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantsExternalDormDto {
    UUID id;
    private String dormName;
    private Set<ParticipantsExternalParticipantDto> participants;
}
