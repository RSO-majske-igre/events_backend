package team.marela.backend.database.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import team.marela.backend.database.BaseEntity;
import team.marela.backend.database.entities.entries.EntryEntity;
import team.marela.backend.database.entities.entries.EntryParticipantInvoiceEntity;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity(name = "participant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ParticipantEntity extends BaseEntity {

    @Column(nullable = false, updatable = false)
    private UUID participantId;

    @ManyToMany(mappedBy = "participants")
    private Set<EntryEntity> entries;

    @OneToMany(mappedBy = "participant")
    private Set<EntryParticipantInvoiceEntity> invoices;
}
