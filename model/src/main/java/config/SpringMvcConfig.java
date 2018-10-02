package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@ComponentScan
public class SpringMvcConfig {

    private int maxUploadSizeInMb = 5 * 1024 * 1024;

    @Bean
    public CommonsMultipartResolver createMultipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(maxUploadSizeInMb * 2);
        resolver.setMaxUploadSizePerFile(maxUploadSizeInMb);
        resolver.setDefaultEncoding("utf-8");
        return resolver;
    }

}
