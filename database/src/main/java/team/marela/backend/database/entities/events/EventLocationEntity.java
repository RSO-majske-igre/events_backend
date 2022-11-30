package team.marela.backend.database.entities.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import team.marela.backend.database.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity(name = "location")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EventLocationEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String locationName;

    @Column(length = 2048)
    private String locationUrl;
    private Double geoLat;
    private Double geoLon;

    @OneToMany(mappedBy = "location")
    private Set<EventEntity> eventsOnLocation;
}
