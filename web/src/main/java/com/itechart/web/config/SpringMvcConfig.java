package com.itechart.web.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.annotation.MultipartConfig;
import java.io.File;

@Configuration
@MultipartConfig
public class SpringMvcConfig implements WebMvcConfigurer {

    private final int maxUploadSizeInMb = 5 * 1024 * 1024;
    private String path = System.getProperty("user.home") + File.separator + "files";

    @Bean
    public CommonsMultipartResolver createMultipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(maxUploadSizeInMb * 2);
        resolver.setMaxUploadSizePerFile(maxUploadSizeInMb);
        resolver.setDefaultEncoding("utf-8");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:///" + path + File.separator);
    }
}
