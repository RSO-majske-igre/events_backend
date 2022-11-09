package team.marela.backend.database.entities.test;

import lombok.*;
import lombok.experimental.SuperBuilder;
import team.marela.backend.database.BaseEntity;

import javax.persistence.Entity;

@Entity(name = "test_entity")
@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TestEntity extends BaseEntity {

    private String name;
}
