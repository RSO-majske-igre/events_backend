package team.marela.backend.api.interfaces.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import team.marela.backend.core.models.test.TestDto;

import javax.validation.Valid;

@RequestMapping("/test")
public interface TestApiInterface {

    @GetMapping
    TestDto test();

    @PostMapping
    TestDto postTest(@RequestBody @Valid TestDto testDto);
}
