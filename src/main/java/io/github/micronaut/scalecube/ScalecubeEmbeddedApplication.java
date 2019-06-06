package io.github.micronaut.scalecube;

import io.github.micronaut.scalecube.discovery.DiscoveryInitializer;
import io.github.micronaut.scalecube.transport.TransportInitializer;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.annotation.Internal;
import io.micronaut.runtime.ApplicationConfiguration;
import io.micronaut.runtime.EmbeddedApplication;
import io.scalecube.services.Microservices;
import io.scalecube.services.ServiceEndpoint;
import io.scalecube.services.annotations.Service;
import io.scalecube.services.discovery.api.ServiceDiscovery;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@Singleton
@Internal
@Replaces(EmbeddedApplication.class)
public class ScalecubeEmbeddedApplication implements EmbeddedApplication<ScalecubeEmbeddedApplication> {

    private final ApplicationContext applicationContext;
    private final ApplicationConfiguration applicationConfiguration;

    private final UnaryOperator<Microservices.ServiceTransportBootstrap> transportBootstrapInitializer;
    private Function<ServiceEndpoint, ServiceDiscovery> serviceDiscoveryInitializer;
    private Microservices microservices;

    public ScalecubeEmbeddedApplication(
            ApplicationContext applicationContext,
            ApplicationConfiguration applicationConfiguration,
            TransportInitializer transportBootstrapInitializer
    ) {
        this.applicationContext = applicationContext;
        this.applicationConfiguration = applicationConfiguration;
        this.transportBootstrapInitializer = transportBootstrapInitializer;
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public ApplicationConfiguration getApplicationConfiguration() {
        return applicationConfiguration;
    }

    @Override
    public boolean isServer() {
        return true;
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public ScalecubeEmbeddedApplication start() {
        Object[] services = applicationContext.getEnvironment().scan(Service.class)
                .map((Function<Class, Collection>) applicationContext::getBeansOfType)
                .flatMap(Collection::stream)
                .toArray();

        Microservices.Builder builder = Microservices.builder()
                .transport(transportBootstrapInitializer)
                .services(services);

        if (Objects.nonNull(serviceDiscoveryInitializer)) {
            builder.discovery(serviceDiscoveryInitializer);
        }
        this.microservices = builder.startAwait();
        applicationContext.registerSingleton(microservices.call());
        return this;
    }

    @Override
    public ScalecubeEmbeddedApplication stop() {
        if (microservices != null) {
            microservices.shutdown().block();
        }
        return this;
    }

    @Inject
    public void setServiceDiscoveryInitializer(Optional<DiscoveryInitializer> serviceDiscovery) {
        serviceDiscovery.ifPresent(discoveryInitializer -> this.serviceDiscoveryInitializer = discoveryInitializer);
    }
}
