package team.marela.backend.database.repositories.events;

import team.marela.backend.database.BaseRepository;
import team.marela.backend.database.entities.events.EventLocationEntity;

import java.util.Optional;

public interface EventLocationRepository extends BaseRepository<EventLocationEntity> {
    Optional<EventLocationEntity> findByLocationName(String locationName);
}
