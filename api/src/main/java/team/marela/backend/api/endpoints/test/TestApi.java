package team.marela.backend.api.endpoints.test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import team.marela.backend.api.interfaces.test.TestApiInterface;
import team.marela.backend.core.models.test.TestDto;
import team.marela.backend.core.services.test.TestService;
import team.marela.backend.database.entities.test.TestEntity;
import team.marela.backend.database.repositories.test.TestRepository;

@RestController
@RequiredArgsConstructor
public class TestApi implements TestApiInterface {

    private final TestRepository testRepository;
    private final TestService testService;

    @GetMapping
    public TestDto test() {
        var uuid = testRepository.save(
                TestEntity.builder().name("test 1").build()
        ).getId();

        return testService.getTestDto(uuid);
    }

    @PostMapping
    public TestDto postTest(TestDto dto) {
        return dto;
    }
}
