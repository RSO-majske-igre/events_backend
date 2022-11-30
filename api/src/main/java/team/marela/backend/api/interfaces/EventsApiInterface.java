package team.marela.backend.api.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.marela.backend.core.models.events.EventDto;
import team.marela.backend.core.models.participants.ParticipantDto;
import team.marela.backend.core.validators.NotNullUUIDValidationGroup;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/events")
public interface EventsApiInterface {

    @GetMapping
    @Operation(summary = "Returns page of events")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns page of data", content = @Content)
    })
    Page<EventDto> getEvents(
            @Parameter(description = "Page of data, starts with 0")
            @RequestParam(required = false, defaultValue = "0")
            Integer page,
            @Parameter(description = "Results per page, default 25")
            @RequestParam(required = false, defaultValue = "25")
            Integer perPage,
            @Parameter(description = "Sorting by field, default name")
            @RequestParam(required = false, defaultValue = "eventName")
            String sortBy
    );

    @GetMapping("/{id}")
    @Operation(summary = "Returns events with given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns event", content = @Content),
            @ApiResponse(responseCode = "404", description = "Event with given ID not found"),
    })
    EventDto getEventById(
            @Parameter(description = "UUID of event")
            @PathVariable UUID id
    );

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates new event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event created", content = @Content)
    })
    EventDto postEvent(
            @Parameter(description = "EventDto to be saved")
            @RequestBody @Valid EventDto event
    );

    @PutMapping
    @Operation(summary = "Updates event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event updated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Event with given UUID not found"),
    })
    EventDto updateEvent(
            @RequestBody
            @Validated(NotNullUUIDValidationGroup.class)
            EventDto event
    );
}