package team.marela.backend.api.interfaces.participants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.marela.backend.core.external.models.participants.ParticipantsExternalParticipantDto;
import team.marela.backend.core.validators.NotNullUUIDValidationGroup;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.UUID;

@RequestMapping("/participants")
public interface ParticipantApiInterface {

//    @GetMapping
//    @Operation(summary = "Returns page of participations")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Returns page of data", content = @Content)
//    })
//    Page<ParticipantDto> getParticipants(
//            @Parameter(description = "Dorm name, not required")
//            @RequestParam(required = false)
//            String dorm,
//            @Parameter(description = "Page of data, starts with 0")
//            @RequestParam(required = false, defaultValue = "0")
//            Integer page,
//            @Parameter(description = "Results per page, default 25")
//            @RequestParam(required = false, defaultValue = "25")
//            Integer perPage,
//            @Parameter(description = "Sorting by field, default name")
//            @RequestParam(required = false, defaultValue = "name")
//            String sortBy
//    );

    @GetMapping("/{id}")
    @Operation(summary = "Returns participant with given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns participant", content = @Content),
            @ApiResponse(responseCode = "404", description = "Participant with given ID not found"),
    })
    ParticipantsExternalParticipantDto getParticipantById(
            @Parameter(description = "UUID of participant")
            @PathVariable UUID id
    );

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates new participant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Participant created", content = @Content)
    })
    ParticipantsExternalParticipantDto postParticipant(
            @Parameter(description = "ParticipantDto to be saved")
            @RequestBody @Valid ParticipantsExternalParticipantDto participant
    );

    @PutMapping
    @Operation(summary = "Updates participant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Participant updated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Participant with given UUID not found"),
    })
    ParticipantsExternalParticipantDto updateParticipant(
            @RequestBody
            @Validated(NotNullUUIDValidationGroup.class)
            ParticipantsExternalParticipantDto participant
    ) throws URISyntaxException;
}
