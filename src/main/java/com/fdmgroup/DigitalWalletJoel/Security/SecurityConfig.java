package com.fdmgroup.DigitalWalletJoel.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author joelongwh
 * SecurityConfig class to manage security configurations and accessibility of web app pages
 */
@Configuration
public class SecurityConfig {
	
	private AuthUserService authUserService;
	private PasswordEncoder encoder;
	
	@Autowired
	public SecurityConfig(AuthUserService authUserService, PasswordEncoder encoder) {
		super();
		this.authUserService = authUserService;
		this.encoder = encoder;
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProv = new DaoAuthenticationProvider();
		authProv.setUserDetailsService(authUserService);
		authProv.setPasswordEncoder(encoder);
		return authProv;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().headers().frameOptions().disable().and().authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/user/newuser").permitAll()
        .antMatchers("/login").authenticated()
        .anyRequest().authenticated()
        .and().formLogin().defaultSuccessUrl("/user/login", true);
		
		return http.build();
	}
	
	

}
