package team.marela.backend.core.models.test;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestDto {
    @NotNull
    private UUID id;
    private String name;
}
