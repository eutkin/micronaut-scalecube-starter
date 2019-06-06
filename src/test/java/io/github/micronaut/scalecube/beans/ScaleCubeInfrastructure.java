package io.github.micronaut.scalecube.beans;

import io.github.micronaut.scalecube.transport.TransportInitializer;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.scalecube.services.transport.rsocket.RSocketServiceTransport;
import io.scalecube.services.transport.rsocket.RSocketTransportResources;

@Factory
public class ScaleCubeInfrastructure {

    @Bean
    public TransportInitializer serviceTransportBootstrap() {
        return bootstrap -> bootstrap.resources(RSocketTransportResources::new)
                .client(RSocketServiceTransport.INSTANCE::clientTransport)
                .server(RSocketServiceTransport.INSTANCE::serverTransport);
    }

}
