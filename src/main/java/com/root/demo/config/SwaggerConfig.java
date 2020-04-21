package com.root.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;

@Configuration
@EnableSwagger2WebMvc
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfig {
    @Bean
    public Docket api() {
       return new Docket(DocumentationType.SWAGGER_2)
               .apiInfo(new ApiInfo(
                       "Demo API",
                       "Description of demo api",
                       "v1",
                       null,
                       null,
                       null,
                       null,
                       new ArrayList<>()))
               .select()
               .apis(RequestHandlerSelectors.any())
               .paths(PathSelectors.any())
               .build()
               .pathMapping("/api");

    }
}
