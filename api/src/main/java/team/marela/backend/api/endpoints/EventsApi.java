package team.marela.backend.api.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import team.marela.backend.api.interfaces.EventsApiInterface;
import team.marela.backend.core.models.events.EventDto;
import team.marela.backend.core.services.EventsService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EventsApi implements EventsApiInterface {

    private final EventsService eventsService;

    @Override
    public Page<EventDto> getEvents(Integer page, Integer perPage, String sortBy) {
        return eventsService.getAll(page, perPage, sortBy);
    }

    @Override
    public EventDto getEventById(UUID id) {
        return eventsService.getById(id);
    }

    @Override
    public EventDto postEvent(EventDto event) {
        return eventsService.saveEvent(event);
    }

    @Override
    public EventDto updateEvent(EventDto event) {
        return eventsService.saveEvent(event);
    }
}
