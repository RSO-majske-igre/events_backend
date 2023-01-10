package team.marela.backend.core.external.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import team.marela.backend.core.external.models.participants.ParticipantDto;

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
    public ParticipantDto getParticipantById(UUID id) {
        return restTemplate.getForEntity(
                String.format("%s/participants/%s", participantsBaseUrl, id.toString()),
                ParticipantDto.class
        ).getBody();
    }

    @SneakyThrows
    public Set<ParticipantDto> getParticipantsById(List<UUID> ids) {
        var res = restTemplate.exchange(
                new URI(String.format("%s/participants/find-by-ids", participantsBaseUrl)),
                HttpMethod.PUT,
                new HttpEntity<>(ids),
                Set.class
        );

        var body = res.getBody();

        return body;
    }

    /**
     * saves participant
     *
     * @param dto
     * @return returns saved participant
     */
    public ParticipantDto saveParticipant(ParticipantDto dto) {
        return restTemplate.postForEntity(
                String.format("%s/participants", participantsBaseUrl),
                dto,
                ParticipantDto.class
        ).getBody();
    }

    public ParticipantDto updateParticipant(ParticipantDto dto) throws URISyntaxException {
        return restTemplate.exchange(
                new URI(String.format("%s/participants", participantsBaseUrl)),
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                ParticipantDto.class
        ).getBody();
    }
}
