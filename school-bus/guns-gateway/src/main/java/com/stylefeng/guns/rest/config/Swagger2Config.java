/**
 * @program school-bus
 * @description: Swagger2Config
 * @author: mf
 * @create: 2020/02/26 18:35
 */

package com.stylefeng.guns.rest.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Setter
@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix = "swagger")
public class Swagger2Config{

    private boolean enable;
    // 不配置取默认值
    private String host;


    private static final String SCHEMA_NAME = "Authorization";
    private static final String KEY_NAME = "Authorization"; //access_token
    private static final String PASS_AS = "header";


    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList = new ArrayList();
        apiKeyList.add(new ApiKey(SCHEMA_NAME, KEY_NAME, PASS_AS));
        return apiKeyList;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return new ArrayList() {{
            add(new SecurityReference("Authorization", authorizationScopes));
        }};
    }

    private List<SecurityContext> securityContexts() {
        return new ArrayList() {{
            add(SecurityContext.builder()
                    .securityReferences(defaultAuth())
                    .forPaths(PathSelectors.any())
                    .build());
        }};
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .host(host)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.stylefeng.guns.rest.modular"))
                .paths(PathSelectors.any())
                .build()
                .enable(enable)
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("school-bus项目后端接口")
                .description("school-bus")
                .version("1.0")
                .build();
    }


}
