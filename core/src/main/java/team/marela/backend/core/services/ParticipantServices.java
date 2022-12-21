package team.marela.backend.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import team.marela.backend.core.models.participants.ParticipantDto;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipantServices {

    @Value("${majskeigre_participants_url}")
    private String participantsBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

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

//    private DormEntity getDorm(String dormName) {
//        return dormRepository.findByDormName(dormName).orElseThrow(DataNotFoundException::new);
//    }
}
