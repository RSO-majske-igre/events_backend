package team.marela.backend.database.repositories.entries;

import team.marela.backend.database.BaseRepository;
import team.marela.backend.database.entities.entries.EntryEntity;
import team.marela.backend.database.entities.events.EventEntity;

import java.util.Set;

public interface EntryRepository extends BaseRepository<EntryEntity> {
    Set<EntryEntity> findByEvent(EventEntity event);
}
