package io.github.micronaut.scalecube.transport;

import io.scalecube.services.Microservices;

import java.util.function.UnaryOperator;

@FunctionalInterface
public interface TransportInitializer extends UnaryOperator<Microservices.ServiceTransportBootstrap> {
}
