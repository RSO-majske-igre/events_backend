package team.marela.backend.database.repositories.participants;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import team.marela.backend.database.BaseRepository;
import team.marela.backend.database.entities.participants.DormEntity;
import team.marela.backend.database.entities.participants.ParticipantEntity;

public interface ParticipantRepository extends BaseRepository<ParticipantEntity> {
    Page<ParticipantEntity> findByDorm(DormEntity dorm, Pageable pagination);
}
