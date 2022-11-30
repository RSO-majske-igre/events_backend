package team.marela.backend.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team.marela.backend.core.exceptions.DataNotFoundException;
import team.marela.backend.core.mappers.DtoMapper;
import team.marela.backend.core.models.participants.ParticipantDto;
import team.marela.backend.database.entities.participants.DormEntity;
import team.marela.backend.database.entities.participants.ParticipantEntity;
import team.marela.backend.database.repositories.participants.DormRepository;
import team.marela.backend.database.repositories.participants.ParticipantRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipantServices {

    private final ParticipantRepository participantRepository;
    private final DormRepository dormRepository;

    private final DtoMapper<ParticipantEntity, ParticipantDto> participantMapper = new DtoMapper<>(ParticipantEntity.class, ParticipantDto.class);

    /**
     * returns all participants with pagination
     *
     * @param page    default 0
     * @param perPage default 25
     * @param sortBy  default name
     * @return page of dtos
     */
    public Page<ParticipantDto> getParticipants(Integer page, Integer perPage, String sortBy) {
        return participantRepository.findAll(PageRequest.of(page, perPage, Sort.by(sortBy))).map(participantMapper::toDto);
    }

    /**
     * returns all participants with pagination who are in defined dorm
     *
     * @param dorm    dorm name
     * @param page    page in pagination
     * @param perPage result per page
     * @param sortBy
     * @return dtos in page
     */
    public Page<ParticipantDto> getParticipantsByDorm(String dorm, Integer page, Integer perPage, String sortBy) {
        return participantRepository.findByDorm(getDorm(dorm), PageRequest.of(page, perPage, Sort.by(sortBy))).map(participantMapper::toDto);
    }

    /**
     * returns participant with given id
     *
     * @param id participant uuid
     * @return participant or throws NoDataFoundException
     */
    public ParticipantDto getParticipantById(UUID id) {
        return participantMapper.toDto(participantRepository.findById(id).orElseThrow(DataNotFoundException::new));
    }

    /**
     * saves participant
     *
     * @param dto
     * @return returns saved participant
     */
    public ParticipantDto saveParticipant(ParticipantDto dto) {
        var entity = participantMapper.toEntity(dto);
        var dorm = upsertDorm(entity.getDorm());
        entity.setDorm(dorm);
        return participantMapper.toDto(
                participantRepository.save(entity)
        );
    }

    public ParticipantDto updateParticipant(ParticipantDto dto) {
        return participantMapper.toDto(
                participantRepository.save(
                        participantMapper.toEntity(dto)
                )
        );
    }

    private DormEntity upsertDorm(DormEntity dormEntity) {
        dormRepository
                .findByDormName(dormEntity.getDormName())
                .ifPresent(dorm -> dormEntity.setId(dorm.getId()));
        return dormRepository.save(dormEntity);
    }

    private DormEntity getDorm(String dormName) {
        return dormRepository.findByDormName(dormName).orElseThrow(DataNotFoundException::new);
    }
}
