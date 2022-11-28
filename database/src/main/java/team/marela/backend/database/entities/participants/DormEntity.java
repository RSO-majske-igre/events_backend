package team.marela.backend.database.entities.participants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import team.marela.backend.database.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity(name = "dorm")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DormEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String dormName;

    @OneToMany(mappedBy = "dorm")
    private Set<ParticipantEntity> participants;
}
