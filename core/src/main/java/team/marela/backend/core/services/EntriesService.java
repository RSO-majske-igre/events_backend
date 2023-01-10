package team.marela.backend.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.marela.backend.core.exceptions.BadRequestException;
import team.marela.backend.core.exceptions.DataNotFoundException;
import team.marela.backend.core.external.models.participants.ParticipantDto;
import team.marela.backend.core.external.services.ParticipantExternalServices;
import team.marela.backend.core.external.services.PaymentExternalServices;
import team.marela.backend.core.mappers.DtoMapper;
import team.marela.backend.core.models.EntryDto;
import team.marela.backend.database.entities.ParticipantEntity;
import team.marela.backend.database.entities.entries.EntryEntity;
import team.marela.backend.database.entities.entries.EntryParticipantInvoiceEntity;
import team.marela.backend.database.entities.events.EventEntity;
import team.marela.backend.database.entities.events.EventResultEntity;
import team.marela.backend.database.repositories.ParticipantRepository;
import team.marela.backend.database.repositories.entries.EntryParticipantInvoiceRepository;
import team.marela.backend.database.repositories.entries.EntryRepository;
import team.marela.backend.database.repositories.events.EventRepository;
import team.marela.backend.database.repositories.events.EventResultRepository;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
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

    //todo: rezultati
    public EntryDto saveEntry(EntryDto dto) {
        var entity = mapper.toEntity(dto);

        var event = eventRepository.findById(dto.getEventId())
                .orElseThrow(DataNotFoundException::new);

        if (event.getStartTime().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Ni mogoče dodajati novih prijav, dogodek je že potekel");
        }

        checkIfParticipantsDoesntExistsOnEvent(event, entity, entity.getParticipants());

        entity = entryRepository.save(
                entity
                        .setParticipants(null)
                        .setEvent(event)
                        .setResult(null)
        );
        var participants = saveParticipants(dto.getParticipants(), entity);
        entity = entity.setParticipants(participants);

//        if (result != null) {
//            entity.setResult(saveEntryResult(result.setEntry(entity)));
//        }

        generateInvoicesForParticipants(participants, entity);

        return mapper.toDto(
                entity
        ).setParticipants(
                participantServices.getParticipantsById(
                        entity.getParticipants().stream()
                                .map(ParticipantEntity::getParticipantId).toList()
                )
        );
    }

    private Set<ParticipantEntity> saveParticipants(Set<ParticipantDto> participants, EntryEntity entry) {
        return participants.stream()
                .map(p -> participantRepository.findByParticipantIdAndEntry(p.getId(), entry)
                        .orElseGet(() ->
                                ParticipantEntity.builder()
                                        .participantId(p.getId())
                                        .entry(entry).build())
                )
                .map(participantRepository::save)
                .collect(Collectors.toSet());
    }

    private EventResultEntity saveEntryResult(EventResultEntity result) {
        return eventResultRepository.save(result);
    }

    private void generateInvoicesForParticipants(Set<ParticipantEntity> participants, EntryEntity entry) {
        participants.forEach(participant ->
                paymentExternalServices.createInvoice(entry, participant)
                        .thenApply(invoice ->
                                entryParticipantInvoiceRepository.save(
                                        EntryParticipantInvoiceEntity.builder()
                                                .invoiceId(invoice.getId())
                                                .participant(participant)
                                                .entry(entry)
                                                .build()
                                )
                        )
        );
    }

    private void checkIfParticipantsDoesntExistsOnEvent(
            EventEntity event,
            EntryEntity entry,
            Set<ParticipantEntity> participants
    ) {
        var eventEntries = entryRepository.findByEvent(event);
        for (var eventEntry : eventEntries) {
            for (var eventEntryParticipant : eventEntry.getParticipants()) {
                for (var participant : participants) {
                    if (
                            participant.getParticipantId().equals(eventEntryParticipant.getParticipantId())
                            && !eventEntry.getId().equals(entry.getId())
                    ) {
                        throw new BadRequestException("Uporabnik s prijavo že obstaja: " + participant.getParticipantId());
                    }
                }
            }
        }
    }
}
