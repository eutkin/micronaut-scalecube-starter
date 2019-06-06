package io.github.micronaut.scalecube.annotation;

import io.micronaut.context.annotation.AliasFor;

import javax.inject.Singleton;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Singleton
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ScaleCubeService {

    @AliasFor(annotation = Singleton.class, member = "value")
    String value() default "";
}
