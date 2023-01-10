package team.marela.backend.database.repositories;

import team.marela.backend.database.BaseRepository;
import team.marela.backend.database.entities.ParticipantEntity;
import team.marela.backend.database.entities.entries.EntryEntity;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ParticipantRepository extends BaseRepository<ParticipantEntity> {
    Optional<ParticipantEntity> findByParticipantIdAndEntry(UUID participantId, EntryEntity entry);
    Set<ParticipantEntity> findByEntry(EntryEntity entry);
}
