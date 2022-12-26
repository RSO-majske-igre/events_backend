package team.marela.backend.core.external.models.participants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.marela.backend.core.models.EntryDto;

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
