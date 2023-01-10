package team.marela.backend.core.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.marela.backend.database.entities.events.EventEntity;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {

    private UUID id;
    private String locationName;
    private String locationUrl;
    private Double geoLat;
    private Double geoLon;

    @JsonIgnoreProperties(value = {
            "entries", "results"
    })
    private Set<EventDto> eventsOnLocation;
}
