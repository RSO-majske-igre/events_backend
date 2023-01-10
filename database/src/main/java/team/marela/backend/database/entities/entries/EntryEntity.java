package team.marela.backend.database.entities.entries;

import lombok.*;
import lombok.experimental.SuperBuilder;
import team.marela.backend.database.BaseEntity;
import team.marela.backend.database.entities.ParticipantEntity;
import team.marela.backend.database.entities.events.EventEntity;
import team.marela.backend.database.entities.events.EventResultEntity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "entry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EntryEntity extends BaseEntity {

    @Builder.Default
    @Column(nullable = false)
    private Boolean paid = false;

    @Builder.Default
    @Column(nullable = false)
    private Boolean accepted = false;

    @ManyToOne
    @JoinColumn(name = "eventId")
    private EventEntity event;

    @OneToMany(mappedBy = "entry", fetch = FetchType.EAGER)
    private Set<ParticipantEntity> participants;

    @OneToOne(mappedBy = "entry")
    private EventResultEntity result;

    @OneToMany(mappedBy = "entry")
    private Set<EntryParticipantInvoiceEntity> invoices;
}
