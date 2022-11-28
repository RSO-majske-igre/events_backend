package team.marela.backend.database.repositories.participants;

import team.marela.backend.database.BaseRepository;
import team.marela.backend.database.entities.participants.DormEntity;

import java.util.Optional;

public interface DormRepository extends BaseRepository<DormEntity> {
    Optional<DormEntity> findByDormName(String dormName);
}
