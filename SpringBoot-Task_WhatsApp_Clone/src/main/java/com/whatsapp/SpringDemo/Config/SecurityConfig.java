package com.whatsapp.SpringDemo.Config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.whatsapp.SpringDemo.JWTSecurity.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtFilter jwtFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/auth/register/user", "/auth/login/user").permitAll() 
	            .requestMatchers("/ws/**").permitAll()   // for web socket
//	            .requestMatchers("/auth/user/**").hasRole("ADMIN")
//	            .requestMatchers("/").hasAnyRole("ADMIN","USER")
//	            .requestMatchers("/").hasAnyRole("ADMIN","USER")
	            .anyRequest().authenticated() 
	        )
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless for JWT no data store at server 
	        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Add the JWT filter before authentication filter

	    return http.build();
	}

    
    @Bean
    public AuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    	provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
    	provider.setUserDetailsService(userDetailsService);
    	return provider;
    }

    @Bean        // we need AuthenticationManager obj when we use jwt token
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
    	return configuration.getAuthenticationManager();    
    }
}
