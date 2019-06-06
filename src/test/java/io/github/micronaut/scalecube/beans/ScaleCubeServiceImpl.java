package io.github.micronaut.scalecube.beans;

import reactor.core.publisher.Flux;

import javax.inject.Singleton;

@Singleton
public class ScaleCubeServiceImpl implements ScaleCubeService {

    @Override
    public Flux<String> upperCase(Flux<String> input) {
        return input.map(String::toUpperCase);
    }
}
