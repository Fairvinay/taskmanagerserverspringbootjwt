package com.dell.mdp.interview.TaskManagerServer.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dell.mdp.interview.TaskManagerServer.SecurityJwtConfig;
import com.dell.mdp.interview.TaskManagerServer.service.JwtService;
import com.dell.mdp.interview.TaskManagerServer.service.UserInfoService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthFilter extends OncePerRequestFilter { 
	
	  private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

	
    private final JwtService jwtService; 
    private final UserInfoService userDetailsService; 
    JwtAuthFilter(JwtService jwtService, UserInfoService userDetailsService) { 
        this.jwtService = jwtService; 
        this.userDetailsService = userDetailsService; 
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { 
        String authHeader = request.getHeader("Authorization"); 
        String token = null; 
        String username = null; 
        
        logger.info("Authorization JwtAuthFilter .....   "); 
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) { 
        	logger.info("Authorization present  ..... "); 
            token = authHeader.substring(7); 
            username = jwtService.extractUsername(token); 
        } 
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { 
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); 
            if (jwtService.validateToken(token, userDetails)) { 
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); 
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); 
                SecurityContextHolder.getContext().setAuthentication(authToken); 
                logger.info("Authorization valid ..... "); 
            } 
        } 
        filterChain.doFilter(request, response); 
    } 
}