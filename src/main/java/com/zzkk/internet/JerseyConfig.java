package com.zzkk.internet;

import com.zzkk.internet.common.annotation.RestResource;
import com.zzkk.internet.common.filter.AuthRequestFilter;
import com.zzkk.internet.common.handler.MissingTokenExceptionMapper;
import com.zzkk.internet.common.handler.MultiUserRepetitionExceptionMapper;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.AcceptHeaderApiListingResource;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
 
import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class JerseyConfig extends ResourceConfig implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    @Value("${spring.jersey.application-path}")
    private String apiPath;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
 
    public JerseyConfig() {
        register(MultiPartFeature.class);
    }
    @PostConstruct
    public void init() {
        // Register components where DI is needed
        this.configureSwagger();
    }
    private void configureSwagger() {
        // Available at localhost:port/swagger.json
        this.register(ApiListingResource.class);
        this.register(AcceptHeaderApiListingResource.class);
        this.register(SwaggerSerializers.class);
        this.register(AuthRequestFilter.class);
        this.register(MissingTokenExceptionMapper.class);
        this.register(MultiUserRepetitionExceptionMapper.class);
        BeanConfig config = new BeanConfig();
        config.setConfigId("Internet Exam");
        config.setTitle("Industrial Park api document");
        config.setVersion("v1");
        config.setContact("Internet");
        config.setSchemes(new String[] { "http" });
        config.setHost("127.0.0.1:8080");
        config.setBasePath(this.apiPath);
        config.setResourcePackage("com.zzkk.internet.api");
        config.setPrettyPrint(true);
        config.setScan(true);
    }

    @Override
    public void afterPropertiesSet() {
        Map<String, Object> restResources = applicationContext.getBeansWithAnnotation(RestResource.class);
        restResources.values().forEach(v -> {register(v.getClass());
            System.out.println(v.getClass());});
    }
}