package team.marela.backend.api.endpoints.participants;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import team.marela.backend.api.interfaces.participants.ParticipantApiInterface;
import team.marela.backend.core.models.participants.ParticipantDto;
import team.marela.backend.core.services.ParticipantServices;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ParticipantApi implements ParticipantApiInterface {

    private final ParticipantServices participantServices;

    @Override
    public Page<ParticipantDto> getParticipants(String dorm, Integer page, Integer perPage, String sortBy) {
        return dorm == null ? participantServices.getParticipants(page, perPage, sortBy) : participantServices.getParticipantsByDorm(dorm, page, perPage, sortBy);
    }

    @Override
    public ParticipantDto getParticipantById(UUID id) {
        return participantServices.getParticipantById(id);
    }

    @Override
    public ParticipantDto postParticipant(ParticipantDto participant) {
        return participantServices.saveParticipant(participant);
    }

    @Override
    public ParticipantDto updateParticipant(ParticipantDto participant) {
        return participantServices.updateParticipant(participant);
    }
}
