package team.marela.backend.api.endpoints.events;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import team.marela.backend.api.interfaces.events.EntriesApiInterface;
import team.marela.backend.core.models.events.EntryDto;
import team.marela.backend.core.services.EntriesService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EntriesApi implements EntriesApiInterface {

    private final EntriesService entriesService;

    @Override
    public EntryDto getEntryById(UUID id) {
        return entriesService.getEntryById(id);
    }

    @Override
    public EntryDto postEntry(EntryDto entry) {
        return entriesService.saveEntry(entry);
    }
}
