package com.example.product_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI productServiceOpenAPI() {
		return new OpenAPI().info(new Info().title("Product Service API")
				.description("Spring Boot microservice with read/write separation").version("1.0.0"));
	}
}
