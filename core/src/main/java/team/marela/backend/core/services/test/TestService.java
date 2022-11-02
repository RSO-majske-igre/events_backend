package team.marela.backend.core.services.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.marela.backend.core.mappers.DtoMapper;
import team.marela.backend.core.models.test.TestDto;
import team.marela.backend.database.entities.test.TestEntity;
import team.marela.backend.database.repositories.test.TestRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;
    private final DtoMapper<TestEntity, TestDto> mapper = new DtoMapper<>(TestEntity.class, TestDto.class);

    public TestDto getTestDto(UUID uuid) {
        return mapper.toDto(testRepository.findById(uuid).get());
    }
}
