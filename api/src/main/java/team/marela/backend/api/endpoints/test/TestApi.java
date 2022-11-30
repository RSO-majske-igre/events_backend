package team.marela.backend.api.endpoints.test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import team.marela.backend.api.interfaces.test.TestApiInterface;

@RestController
@RequiredArgsConstructor
public class TestApi implements TestApiInterface {
//
//    private final TestRepository testRepository;
//    private final TestService testService;
//
//    @GetMapping
//    public TestDto test() {
//        var uuid = testRepository.save(
//                TestEntity.builder().name("test 1").build()
//        ).getId();
//
//        return testService.getTestDto(uuid);
//    }
//
//    @PostMapping
//    public TestDto postTest(TestDto dto) {
//        return dto;
//    }
}
