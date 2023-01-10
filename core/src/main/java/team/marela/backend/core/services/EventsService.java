package team.marela.backend.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team.marela.backend.core.exceptions.DataNotFoundException;
import team.marela.backend.core.external.services.ParticipantExternalServices;
import team.marela.backend.core.mappers.DtoMapper;
import team.marela.backend.core.models.EntryDto;
import team.marela.backend.core.models.EventDto;
import team.marela.backend.database.entities.ParticipantEntity;
import team.marela.backend.database.entities.entries.EntryEntity;
import team.marela.backend.database.entities.events.EventEntity;
import team.marela.backend.database.entities.events.EventLocationEntity;
import team.marela.backend.database.repositories.events.EventLocationRepository;
import team.marela.backend.database.repositories.events.EventRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventsService {

    private final EventRepository eventRepository;
    private final EventLocationRepository eventLocationRepository;
    private final ParticipantExternalServices participantExternalServices;

    private final DtoMapper<EventEntity, EventDto> mapper = new DtoMapper<>(EventEntity.class, EventDto.class);
    private final DtoMapper<EntryEntity, EntryDto> entryMapper = new DtoMapper<>(EntryEntity.class, EntryDto.class);


    public EventDto getById(UUID id) {
        var entity = eventRepository.findById(id).orElseThrow(DataNotFoundException::new);

        var entries = new ArrayList<EntryDto>();

        for(var entry: entity.getEntries()) {
            var entryDto = entryMapper.toDto(entry);
            entryDto.setParticipants(
                    participantExternalServices.getParticipantsById(entry.getParticipants().stream().map(ParticipantEntity::getParticipantId).toList())
            );
            entries.add(entryDto);
        }

        return mapper.toDto(entity).setEntries(new HashSet<>(entries));
    }

    public Page<EventDto> getAll(Integer pageNo, Integer perPage, String sortBy) {
        var page = eventRepository.findAll(PageRequest.of(pageNo, perPage, Sort.by(sortBy)));
        var x=  page.map(mapper::toDto);
        return x;
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
