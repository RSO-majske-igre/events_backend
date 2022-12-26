package team.marela.backend.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team.marela.backend.core.exceptions.DataNotFoundException;
import team.marela.backend.core.mappers.DtoMapper;
import team.marela.backend.core.models.EventDto;
import team.marela.backend.database.entities.events.EventEntity;
import team.marela.backend.database.entities.events.EventLocationEntity;
import team.marela.backend.database.repositories.events.EventLocationRepository;
import team.marela.backend.database.repositories.events.EventRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventsService {

    private final EventRepository eventRepository;
    private final EventLocationRepository eventLocationRepository;

    private final DtoMapper<EventEntity, EventDto> mapper = new DtoMapper<>(EventEntity.class, EventDto.class);


    public EventDto getById(UUID id) {
        return mapper.toDto(eventRepository.findById(id).orElseThrow(DataNotFoundException::new));
    }

    public Page<EventDto> getAll(Integer pageNo, Integer perPage, String sortBy) {
        return eventRepository.findAll(PageRequest.of(pageNo, perPage, Sort.by(sortBy))).map(mapper::toDto);
    }

    public EventDto saveEvent(EventDto dto) {
        var entity = mapper.toEntity(dto);
        entity.setLocation(upsertEventLocation(entity.getLocation()));
        return mapper.toDto(eventRepository.save(entity));
    }

    private EventLocationEntity upsertEventLocation(EventLocationEntity location) {
        eventLocationRepository.findByLocationName(location.getLocationName())
                .ifPresent(l -> location.setId(l.getId()));

        return eventLocationRepository.save(location);
    }
}
