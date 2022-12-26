package team.marela.backend.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.marela.backend.core.exceptions.DataNotFoundException;
import team.marela.backend.core.external.services.ParticipantExternalServices;
import team.marela.backend.core.external.services.PaymentExternalServices;
import team.marela.backend.core.mappers.DtoMapper;
import team.marela.backend.core.models.EntryDto;
import team.marela.backend.database.entities.ParticipantEntity;
import team.marela.backend.database.entities.entries.EntryEntity;
import team.marela.backend.database.entities.entries.EntryParticipantInvoiceEntity;
import team.marela.backend.database.entities.events.EventResultEntity;
import team.marela.backend.database.repositories.ParticipantRepository;
import team.marela.backend.database.repositories.entries.EntryParticipantInvoiceRepository;
import team.marela.backend.database.repositories.entries.EntryRepository;
import team.marela.backend.database.repositories.events.EventRepository;
import team.marela.backend.database.repositories.events.EventResultRepository;

import java.net.URISyntaxException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntriesService {


    private final ParticipantExternalServices participantServices;
    private final PaymentExternalServices paymentExternalServices;
    private final EntryRepository entryRepository;
    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;
    private final EventResultRepository eventResultRepository;
    private final EntryParticipantInvoiceRepository entryParticipantInvoiceRepository;
    private final DtoMapper<EntryEntity, EntryDto> mapper = new DtoMapper<>(EntryEntity.class, EntryDto.class);

    public EntryDto getEntryById(UUID id) throws URISyntaxException {
        var entity = entryRepository.findById(id)
                .orElseThrow(DataNotFoundException::new);
        return mapper.toDto(
                entity
        ).setParticipants(
                participantServices.getParticipantsById(entity.getParticipants().stream().map(ParticipantEntity::getParticipantId).toList())
        );
    }

    //todo
    public EntryDto saveEntry(EntryDto dto) throws URISyntaxException {
        var entity = mapper.toEntity(dto);
        var result =
                dto.getId() != null ?
                        entity.getResult() : null;
        var event = eventRepository.findById(entity.getEvent().getId())
                .orElseThrow(DataNotFoundException::new);

        var participants = saveParticipants(entity.getParticipants());
        entity = entryRepository.save(
                entity
                        .setEvent(event)
                        .setParticipants(participants)
                        .setResult(null)
        );

        if (result != null) {
            entity.setResult(saveEntryResult(result.setEntry(entity)));
        }

        return mapper.toDto(
                entity
        ).setParticipants(
                participantServices.getParticipantsById(
                        entity.getParticipants().stream()
                                .map(ParticipantEntity::getParticipantId).toList()
                )
        ).setInvoices(
                generateInvoicesForParticipants(participants, entity).stream()
                        .map(EntryParticipantInvoiceEntity::getInvoiceId)
                        .collect(Collectors.toSet())
        );
    }

    private Set<ParticipantEntity> saveParticipants(Set<ParticipantEntity> participants) {
        return participants.stream().map(participantRepository::save)
                .collect(Collectors.toSet());
    }

    private EventResultEntity saveEntryResult(EventResultEntity result) {
        return eventResultRepository.save(result);
    }

    private Set<EntryParticipantInvoiceEntity> generateInvoicesForParticipants(Set<ParticipantEntity> participants, EntryEntity entry) {
        return participants.stream().map(participant -> {
                    var invoice = paymentExternalServices.createInvoice(entry, participant);
                    return EntryParticipantInvoiceEntity.builder()
                            .invoiceId(invoice.getId())
                            .participant(participant)
                            .entry(entry)
                            .build();

                })
                .map(entryParticipantInvoiceRepository::save)
                .collect(Collectors.toSet());
    }
}
