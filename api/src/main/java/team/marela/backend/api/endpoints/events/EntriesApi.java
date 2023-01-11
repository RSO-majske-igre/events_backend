package team.marela.backend.api.endpoints.events;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import team.marela.backend.api.interfaces.events.EntriesApiInterface;
import team.marela.backend.core.models.EntryDto;
import team.marela.backend.core.services.EntriesService;

import java.net.URISyntaxException;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class EntriesApi implements EntriesApiInterface {

    private final EntriesService entriesService;

    @Override
    public EntryDto getEntryById(UUID id) throws URISyntaxException {
        return entriesService.getEntryById(id);
    }

    @Override
    @Timed(value = "entries__post", description = "Time of processing for entry posting", percentiles = {0.25, 0.95})
    public EntryDto postEntry(EntryDto entry) {
        return entriesService.saveEntry(entry);
    }
}
