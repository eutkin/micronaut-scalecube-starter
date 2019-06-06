package io.github.micronaut.scalecube;

import io.github.micronaut.scalecube.beans.ScaleCubeService;
import io.micronaut.test.annotation.MicronautTest;
import io.scalecube.services.ServiceCall;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest(packages = "io.github.micronaut.scalecube")
class ScalecubeEmbeddedApplicationTest {

    @Inject
    private ServiceCall serviceCall;

    @Test
    void name() {
        ScaleCubeService service = serviceCall.api(ScaleCubeService.class);
        Flux<String> result = service.upperCase(Flux.just("string"));
        String actual = result.blockLast();
        assertEquals("STRING", actual);
    }
}