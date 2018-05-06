package me.efraimgentil.jsr303.config;

import me.efraimgentil.jsr303.resource.SampleResource;
import me.efraimgentil.jsr303.resource.UserResource;
import me.efraimgentil.jsr303.resource.exceptionmapper.ValidationExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages= { "me.efraimgentil.jsr303"  } )
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(SampleResource.class);
        register(UserResource.class);
        register(ValidationExceptionMapper.class);

        register(ValidationConfiguration.class);

    }

}
