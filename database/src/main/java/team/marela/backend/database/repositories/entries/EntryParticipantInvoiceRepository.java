package team.marela.backend.database.repositories.entries;

import team.marela.backend.database.BaseRepository;
import team.marela.backend.database.entities.entries.EntryEntity;
import team.marela.backend.database.entities.entries.EntryParticipantInvoiceEntity;

import java.util.Set;

public interface EntryParticipantInvoiceRepository extends BaseRepository<EntryParticipantInvoiceEntity> {
    Set<EntryParticipantInvoiceEntity> findByEntry(EntryEntity entry);
}
