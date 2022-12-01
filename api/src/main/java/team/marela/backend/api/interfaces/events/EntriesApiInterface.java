package team.marela.backend.api.interfaces.events;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.marela.backend.core.models.events.EntryDto;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/events/entries")
public interface EntriesApiInterface {


//    @GetMapping
//    @Operation(summary = "Returns page of entries for event")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Returns page of data", content = @Content)
//    })
//    Page<EntryDto> getEntries(
//            @Parameter(description = "Page of data, starts with 0")
//            @RequestParam(required = false, defaultValue = "0")
//            Integer page,
//            @Parameter(description = "Results per page, default 25")
//            @RequestParam(required = false, defaultValue = "25")
//            Integer perPage,
//            @Parameter(description = "Sorting by field, default name")
//            @RequestParam(required = false, defaultValue = "eventName")
//            String sortBy
//    );

    @GetMapping("/{id}")
    @Operation(summary = "Returns entity with given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns entity", content = @Content),
            @ApiResponse(responseCode = "404", description = "Entity with given ID not found"),
    })
    EntryDto getEntryById(
            @Parameter(description = "UUID of entity")
            @PathVariable UUID id
    );

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates or updates new entity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entity created or updated", content = @Content)
    })
    EntryDto postEntry(
            @Parameter(description = "Entity to be saved")
            @RequestBody @Valid EntryDto entry
    );
}
