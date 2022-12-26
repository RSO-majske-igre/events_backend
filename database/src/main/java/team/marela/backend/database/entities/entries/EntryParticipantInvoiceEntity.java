package team.marela.backend.database.entities.entries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import team.marela.backend.database.BaseEntity;
import team.marela.backend.database.entities.ParticipantEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity(name = "entry_participant_invoice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EntryParticipantInvoiceEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private UUID invoiceId;

    @ManyToOne
    @JoinColumn(name = "participant")
    private ParticipantEntity participant;

    @ManyToOne
    @JoinColumn(name = "entry")
    private EntryEntity entry;
}
