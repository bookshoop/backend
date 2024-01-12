package com.project.bookforeast.common.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project.bookforeast.common.domain.error.ExceptionHandlerFilter;
import com.project.bookforeast.common.security.filter.JwtAuthorizationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	JwtAuthorizationFilter jwtAuthorizationFilter;
	

	ExceptionHandlerFilter exceptionHandlerFilter;
	
	@Autowired
	public SecurityConfig(JwtAuthorizationFilter jwtAuthorizationFilter, ExceptionHandlerFilter exceptionHandlerFilter) {
		this.jwtAuthorizationFilter = jwtAuthorizationFilter;
		this.exceptionHandlerFilter = exceptionHandlerFilter;
	}
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(authorizeRequests ->
				authorizeRequests
					.requestMatchers("/", 
									 "/api/u/v1/token",
									 "/api/u/v1/social-login",
									 "/api-docs", 
									 "/api-docs/**",
									 "/swagger-ui/**",
									 "/error",
									 "/",
									 "/swagger-config",
									 "/api/n/v1/**",
									 "/api/u/v1/books/best-seller",
									 "/api/u/v1/book/**"
								).permitAll()
					.requestMatchers(
						"/api/u/v1/**"	
					).hasAnyRole("USER", "MANAGER")
			)
			.csrf(csrf -> {
				csrf.disable();
			})
			.cors(cors -> {
				cors.disable();
			})
			.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(exceptionHandlerFilter, JwtAuthorizationFilter.class)
			.logout(logout -> logout.logoutSuccessUrl("/").permitAll());
			
		
		return http.build();
	}
	

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
        											    "/favicon.ico",
        											    "/swagger-ui/**",
        											    "/",
        											    "/swagger-config",
        											    "/error"
        											   );
    }

}
