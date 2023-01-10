package team.marela.backend.api.endpoints.locations;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import team.marela.backend.api.interfaces.locations.LocationsApiInterface;
import team.marela.backend.core.models.LocationDto;
import team.marela.backend.core.services.LocationsService;

import java.util.List;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class LocationsApi implements LocationsApiInterface {

    private final LocationsService locationsService;

    @Override
    public List<LocationDto> getLocations() {
        return locationsService.getLocations();
    }

    @Override
    public LocationDto getLocationById(UUID id) {
        return locationsService.getLocationById(id);
    }

    @Override
    public LocationDto postLocation(LocationDto location) {
        return locationsService.upsertLocation(location);
    }

    @Override
    public LocationDto putLocation(LocationDto location) {
        return locationsService.upsertLocation(location);
    }
}
