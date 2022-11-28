package team.marela.backend.api.interfaces.participants;

import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.marela.backend.core.models.participants.ParticipantDto;
import team.marela.backend.core.validators.NotNullUUIDValidationGroup;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/participants")
public interface ParticipantApiInterface {

    @GetMapping
    Page<ParticipantDto> getParticipants(
            @RequestParam(required = false) String dorm,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "25") Integer perPage,
            @RequestParam(required = false, defaultValue = "name") String sortBy
    );

    @GetMapping("/{id}")
    ParticipantDto getParticipantById(@PathVariable UUID id);

    @PostMapping
    ParticipantDto postParticipant(
            @RequestBody @Valid ParticipantDto participant
    );

    @PutMapping
    ParticipantDto updateParticipant(
            @RequestBody
            @Validated(NotNullUUIDValidationGroup.class)
            ParticipantDto participant
    );
}
