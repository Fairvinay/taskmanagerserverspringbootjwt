package com.dell.mdp.interview.TaskManagerServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.dell.mdp.interview.TaskManagerServer.filter.JwtAuthFilter;
import com.dell.mdp.interview.TaskManagerServer.repository.UserInfoRepository;
import com.dell.mdp.interview.TaskManagerServer.service.UserInfoService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityJwtConfig { 
	
	  private static final Logger logger = LoggerFactory.getLogger(SecurityJwtConfig.class);
	
	@Autowired
    private   JwtAuthFilter authFilter; 
    
    public SecurityJwtConfig(JwtAuthFilter authFilter) { 
        this.authFilter = authFilter; 
    }
    // User Creation 
    @Bean
    public UserDetailsService userDetailsService(UserInfoRepository repository, PasswordEncoder passwordEncoder) { 
        return new UserInfoService(repository, passwordEncoder); 
    } 
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception { 
    	
    	logger.info("fiterChain .... " ); 
    	
    	return http.cors().configurationSource(corsConfigurationSource()) 
    			.and()
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/auth/generateToken", "/auth/register").permitAll()
                .requestMatchers("/auth/hello").authenticated()
                .requestMatchers("/api/tasks/**").authenticated()
            )    // input to httpBasic(withDefaults()) (csrf) -> csrf.disable()
            .httpBasic().and().csrf().disable()  
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    	
    	  logger.info("authenticationProvider.... " ); 
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { 
    	 logger.info("authenticationManager.... " ); 
        return config.getAuthenticationManager(); 
    } 
    
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow all origins or specific origins
        config.addAllowedOrigin("http://localhost:4200"); // Single origin
       // config.addAllowedOrigin("http://another-origin.com"); // Another origin

        // Or allow all origins (not recommended for production)
        // config.addAllowedOriginPattern("*");

        // Allow specific HTTP methods (GET, POST, etc.)
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");

        // Allow specific headers (e.g., for custom headers)
        config.addAllowedHeader("Authorization");
        config.addAllowedHeader("Content-Type");

        // Allow credentials (cookies, etc.)
        config.setAllowCredentials(true);

        // Set the maximum age of the pre-flight request (in seconds)
        config.setMaxAge(3600L);  // 1 hour

        // Register the CORS configuration for all endpoints
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    
    
    
    
    
}