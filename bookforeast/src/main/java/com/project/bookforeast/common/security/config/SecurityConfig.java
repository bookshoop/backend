package com.project.bookforeast.common.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project.bookforeast.common.security.filter.JwtAuthorizationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	JwtAuthorizationFilter jwtAuthorizationFilter;
	
	
	@Autowired
	public SecurityConfig(JwtAuthorizationFilter jwtAuthorizationFilter) {
		this.jwtAuthorizationFilter = jwtAuthorizationFilter;
	}
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(authorizeRequests ->
				authorizeRequests
					.requestMatchers("/", 
									 "/api/u/v1/social-login",
									 "/api-docs", 
									 "/api-docs/**",
									 "/swagger-ui/**",
									 "/api/u/v1/token",
									 "/error"
								).permitAll()
					.requestMatchers(
						"/api/u/v1/user"	
					).hasAnyRole("USER", "ADMIN")
			)
			.csrf(csrf -> {
				csrf.disable();
			})
			.cors(cors -> {
				cors.disable();
			})
			.httpBasic(Customizer.withDefaults())
			.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
			.logout(logout -> logout.logoutSuccessUrl("/").permitAll());
			
		
		return http.build();
	}
	

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
        											    "/favicon.ico",
        											    "/swagger-ui/**"
        											   );
    }

}
