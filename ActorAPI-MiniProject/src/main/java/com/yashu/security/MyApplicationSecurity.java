package com.yashu.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class MyApplicationSecurity {
	
	@Autowired
	DataSource dataSource;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf(csrf->csrf.disable());
		
		http.authorizeHttpRequests(
				auth -> auth.requestMatchers(HttpMethod.DELETE,"/actor-api/delete/{id}")
				            .hasAnyRole("ADMIN")
				            .requestMatchers("/actor-api/update").authenticated()
				            .requestMatchers("/**")
				            .permitAll()
					).formLogin(Customizer.withDefaults());
		
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	/*
	 * @Bean public UserDetailsService userDetailsService() {
	 * 
	 * UserDetails admin = User.builder().username("Allen")
	 * .password(passwordEncoder().encode("allen@123")).roles("ADMIN").build();
	 * 
	 * UserDetails user = User.builder().username("Guest")
	 * .password(passwordEncoder().encode("guest")).roles("USER").build();
	 * 
	 * return new InMemoryUserDetailsManager(admin,user); }
	 */
	@Bean
	public UserDetailsService userDetailsService() {
		return new JdbcUserDetailsManager(dataSource);
	}
}
