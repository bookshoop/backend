
package com.project.bookforeast.common.swagger.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "bookforeast", version = "v1"))
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {
	
	@Bean
	public GroupedOpenApi openAPI() {
		return GroupedOpenApi.builder()
				.group("user")
				.pathsToMatch("/api/u/v1/**")
				.build();
	}
}
