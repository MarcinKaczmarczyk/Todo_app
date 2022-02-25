package io.github.MarcinK.todoapp;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

@EnableAsync
@SpringBootApplication
public class TodoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoAppApplication.class, args);
	}

	@Bean
	KeycloakSpringBootConfigResolver keycloakConfigResolver(){
		return new KeycloakSpringBootConfigResolver();
	}

	@Bean
	Validator validator() {
	return new LocalValidatorFactoryBean();
	}
}
