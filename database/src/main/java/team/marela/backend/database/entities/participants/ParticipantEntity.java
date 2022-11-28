package team.marela.backend.database.entities.participants;

import lombok.*;
import lombok.experimental.SuperBuilder;
import team.marela.backend.database.BaseEntity;
import team.marela.backend.database.entities.entries.EntryEntity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "participant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ParticipantEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "dormId")
    private DormEntity dorm;

    @ManyToMany(mappedBy = "participants")
    private Set<EntryEntity> entries;
}
