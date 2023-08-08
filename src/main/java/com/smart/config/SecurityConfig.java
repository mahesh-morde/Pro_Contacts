package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import com.smart.services.MyUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	
	@Autowired
	private MyUserDetailService detailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
		
	@Bean
	public SecurityFilterChain config(HttpSecurity http) throws Exception{
		
		http.csrf().disable()		
		.authorizeRequests()
		.requestMatchers("/admin/**").hasAuthority("ADMIN")
//		.requestMatchers("/user/**").hasAnyAuthority("ADMIN", "USER")
		.anyRequest().permitAll()
		.and()
		.httpBasic(Customizer.withDefaults())
		.formLogin(form -> form
				.loginPage("/login").permitAll()
//				.loginProcessingUrl("/login")
//				.defaultSuccessUrl("/user/index")
//				.failureUrl("/login_error")
				)
		.authenticationProvider(authenticationProvider());
		
		return http.build();
	}
	
	
	
	@Bean
	public AuthenticationManager authenticationManager (AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();		
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.detailsService);
		provider.setPasswordEncoder(this.passwordEncoder());
		return provider;
	}
	
	@Bean
	public UserDetailsService users() {
		
		UserDetails user = User.builder()
				.username("User")
				.password(passwordEncoder().encode("User@123"))
				.roles("USER")
				.build();
		
		UserDetails admin = User.builder()
				.username("Admin")
				.password(passwordEncoder().encode("Admin@123"))
				.roles("USER", "ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(user, admin);
	}

}
