package team.marela.backend.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import team.marela.backend.core.exceptions.DataNotFoundException;
import team.marela.backend.core.mappers.DtoMapper;
import team.marela.backend.core.models.LocationDto;
import team.marela.backend.database.entities.events.EventLocationEntity;
import team.marela.backend.database.repositories.events.EventLocationRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationsService {

    private final EventLocationRepository eventLocationRepository;

    private final DtoMapper<EventLocationEntity, LocationDto> mapper = new DtoMapper<>(EventLocationEntity.class, LocationDto.class);

    public List<LocationDto> getLocations() {
        return eventLocationRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public LocationDto getLocationById(UUID locationId) {
        return mapper.toDto(
                eventLocationRepository.findById(locationId)
                        .orElseThrow(DataNotFoundException::new)
        );
    }

    public LocationDto upsertLocation(LocationDto dto) {
        return mapper.toDto(
                eventLocationRepository.save(
                        mapper.toEntity(dto)
                )
        );
    }

}
