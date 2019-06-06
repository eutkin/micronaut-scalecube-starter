package io.github.micronaut.scalecube.beans;

import io.scalecube.services.annotations.Service;
import io.scalecube.services.annotations.ServiceMethod;
import reactor.core.publisher.Flux;

@Service("test-service")
public interface ScaleCubeService {

    @ServiceMethod("test-api")
    Flux<String> upperCase(Flux<String> input);
}
