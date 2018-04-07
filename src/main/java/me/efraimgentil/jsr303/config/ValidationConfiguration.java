package me.efraimgentil.jsr303.config;

import org.glassfish.jersey.server.validation.ValidationConfig;
import org.hibernate.validator.parameternameprovider.ParanamerParameterNameProvider;
import org.hibernate.validator.parameternameprovider.ReflectionParameterNameProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.ws.rs.ext.ContextResolver;

public class ValidationConfiguration implements ContextResolver<ValidationConfig> {

    @Override
    public ValidationConfig getContext(Class<?> aClass) {
        ValidationConfig config = new ValidationConfig();
        config.parameterNameProvider(new ParanamerParameterNameProvider());
        return config;
    }
}
