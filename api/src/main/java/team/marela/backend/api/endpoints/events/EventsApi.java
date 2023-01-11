package team.marela.backend.api.endpoints.events;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import team.marela.backend.api.interfaces.events.EventsApiInterface;
import team.marela.backend.core.models.EventDto;
import team.marela.backend.core.services.EventsService;

import javax.annotation.PostConstruct;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class EventsApi implements EventsApiInterface {

    private final EventsService eventsService;
    private final MeterRegistry meterRegistry;

    private Counter getEventsMetricCounter;

    @PostConstruct
    private void createCounters() {
        getEventsMetricCounter = Counter
                .builder("events__getEventsRequestsCounter")
                .description("Counts number of times GET /events was called")
                .tag("type", "counter")
                .register(meterRegistry);

    }

    @Override
    public Page<EventDto> getEvents(Integer page, Integer perPage, String sortBy) {
        getEventsMetricCounter.increment(1);
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
