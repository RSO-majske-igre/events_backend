package team.marela.backend.database.entities.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import team.marela.backend.database.BaseEntity;
import team.marela.backend.database.entities.entries.EntryEntity;

import javax.persistence.*;

@Entity(name = "event_result")
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "unique_event_result",
                columnNames = {
                        "place", "eventId", "entryId"
                }
        ),
        @UniqueConstraint(
                name = "unique_event_entry",
                columnNames = {"eventId", "entryId"}
        )
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EventResultEntity extends BaseEntity {

    @Column(nullable = false)
    private Integer place;

    private Integer points;

    @ManyToOne
    @JoinColumn(name = "eventId", nullable = false)
    private EventEntity event;

    @OneToOne
    @JoinColumn(name = "entryId", nullable = false)
    private EntryEntity entry;
}
