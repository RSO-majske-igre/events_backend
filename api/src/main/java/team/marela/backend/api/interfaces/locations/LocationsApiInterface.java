package team.marela.backend.api.interfaces.locations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import team.marela.backend.core.external.models.participants.ParticipantDto;
import team.marela.backend.core.models.LocationDto;

import java.util.List;
import java.util.UUID;

@RequestMapping("/locations")
public interface LocationsApiInterface {

    @GetMapping()
    @Operation(summary = "Returns locations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns locations", content = @Content),
    })
    List<LocationDto> getLocations();

    @GetMapping("/{id}")
    @Operation(summary = "Returns location with given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns location", content = @Content),
            @ApiResponse(responseCode = "404", description = "Location with given ID not found"),
    })
    LocationDto getLocationById(
            @Parameter(description = "UUID of location")
            @PathVariable UUID id
    );

    @PostMapping
    @Operation(summary = "Saves location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Location succesfully created")
    })
    LocationDto postLocation(
            @RequestBody LocationDto location
    );

    @PutMapping
    @Operation(summary = "Updates location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Location succesfully created")
    })
    LocationDto putLocation(
            @RequestBody LocationDto location
    );
}
