package team.marela.backend.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.marela.backend.core.mappers.DtoMapper;
import team.marela.backend.core.models.participants.ParticipantDto;
import team.marela.backend.database.entities.participants.DormEntity;
import team.marela.backend.database.entities.participants.ParticipantEntity;
import team.marela.backend.database.repositories.participants.DormRepository;
import team.marela.backend.database.repositories.participants.ParticipantRepository;

@Service
@RequiredArgsConstructor
public class ParticipantServices {

    private final ParticipantRepository participantRepository;
    private final DormRepository dormRepository;

    private final DtoMapper<ParticipantEntity, ParticipantDto> participantMapper = new DtoMapper<>(ParticipantEntity.class, ParticipantDto.class);

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
}
