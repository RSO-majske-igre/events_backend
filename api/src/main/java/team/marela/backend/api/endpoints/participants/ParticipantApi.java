package team.marela.backend.api.endpoints.participants;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import team.marela.backend.api.interfaces.participants.ParticipantApiInterface;
import team.marela.backend.core.external.models.participants.ParticipantsExternalParticipantDto;
import team.marela.backend.core.external.services.ParticipantExternalServices;

import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ParticipantApi implements ParticipantApiInterface {

    private final ParticipantExternalServices participantServices;

//    @Override
//    public Page<ParticipantDto> getParticipants(String dorm, Integer page, Integer perPage, String sortBy) {
//        return dorm == null ? participantServices.getParticipants(page, perPage, sortBy) : participantServices.getParticipantsByDorm(dorm, page, perPage, sortBy);
//    }

    @Override
    public ParticipantsExternalParticipantDto getParticipantById(UUID id) {
        return participantServices.getParticipantById(id);
    }

    @Override
    public ParticipantsExternalParticipantDto postParticipant(ParticipantsExternalParticipantDto participant) {
        return participantServices.saveParticipant(participant);
    }

    @Override
    public ParticipantsExternalParticipantDto updateParticipant(ParticipantsExternalParticipantDto participant) throws URISyntaxException {
        return participantServices.updateParticipant(participant);
    }
}
