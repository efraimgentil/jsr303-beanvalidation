package me.efraimgentil.jsr303.config;

import me.efraimgentil.jsr303.resource.SampleResource;
import me.efraimgentil.jsr303.resource.exceptionmapper.ValidationExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(SampleResource.class);

        register(ValidationExceptionMapper.class);
    }

}
