package team.marela.backend.core.external.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import team.marela.backend.core.external.models.participants.ParticipantsExternalParticipantDto;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipantExternalServices {

    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${external.url.majskeigre_participants_url}")
    private String participantsBaseUrl;

    /**
     * returns participant with given id
     *
     * @param id participant uuid
     * @return participant or throws NoDataFoundException
     */
    public ParticipantsExternalParticipantDto getParticipantById(UUID id) {
        return restTemplate.getForEntity(
                String.format("%s/participants/%s", participantsBaseUrl, id.toString()),
                ParticipantsExternalParticipantDto.class
        ).getBody();
    }

    public Set<ParticipantsExternalParticipantDto> getParticipantsById(List<UUID> ids) throws URISyntaxException {
        return (Set<ParticipantsExternalParticipantDto>) (restTemplate.exchange(
                new URI(String.format("%s/participants/find-by-ids", participantsBaseUrl)),
                HttpMethod.PUT,
                new HttpEntity<>(ids),
                Set.class
        ).getBody());
    }

    /**
     * saves participant
     *
     * @param dto
     * @return returns saved participant
     */
    public ParticipantsExternalParticipantDto saveParticipant(ParticipantsExternalParticipantDto dto) {
        return restTemplate.postForEntity(
                String.format("%s/participants", participantsBaseUrl),
                dto,
                ParticipantsExternalParticipantDto.class
        ).getBody();
    }

    public ParticipantsExternalParticipantDto updateParticipant(ParticipantsExternalParticipantDto dto) throws URISyntaxException {
        return restTemplate.exchange(
                new URI(String.format("%s/participants", participantsBaseUrl)),
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                ParticipantsExternalParticipantDto.class
        ).getBody();
    }

//    private DormEntity getDorm(String dormName) {
//        return dormRepository.findByDormName(dormName).orElseThrow(DataNotFoundException::new);
//    }
}
