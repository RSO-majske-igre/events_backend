package team.marela.backend.database.entities.events;

import lombok.*;
import lombok.experimental.SuperBuilder;
import team.marela.backend.database.BaseEntity;
import team.marela.backend.database.entities.entries.EntryEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EventEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String eventName;

    @Column(length = 2048)
    private String description;

    @Column(nullable = false)
    private LocalDateTime startTime;

    private BigDecimal pricePerEntry;

    @Column(nullable = false)
    private String teamDescription;

    @Column(length = 2048)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "locationId")
    private EventLocationEntity location;

    @OneToMany(mappedBy = "event")
    private Set<EntryEntity> entries;

    @OneToMany(mappedBy = "event")
    private Set<EventResultEntity> results;
}
