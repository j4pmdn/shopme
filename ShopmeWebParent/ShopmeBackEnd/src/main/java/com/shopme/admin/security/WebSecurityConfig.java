package com.shopme.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
    public UserDetailsService userDetailsService() {
        return new ShopmeUserDetailsService();
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        
        return authProvider;
    }
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.authenticationProvider(authenticationProvider());

	    http.authorizeHttpRequests(auth -> auth
	    	.requestMatchers("/users/**").hasAuthority("Admin")
	    	.requestMatchers("/categories/**", "/brands/**").hasAnyAuthority("Admin", "Editor")
	        .anyRequest().authenticated()
	    )
        .formLogin(form -> form
                        .loginPage("/login") // Specify custom login page URL
                        .usernameParameter("email") // Set username parameter to "email"
                        .permitAll() // Allow access to the login page
        )
        .logout(logout -> logout.permitAll())
        .rememberMe(rem -> rem
        		.key("AbcDefgHijKlmnOpqrs_1234567890")
        		.tokenValiditySeconds(7 * 24 * 60 * 60)
        		);

	    return http.build();
	}
	
	@Bean
	WebSecurityCustomizer configWebsecurity() throws Exception {
		return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
	}
	
}
