package com.dell.mdp.interview.TaskManagerServer;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomForCookiePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	 @Autowired
	    private  JwtUtil jwtUtil;

	public CustomForCookiePasswordAuthenticationFilter(JwtUtil jwtUtil) {
	        this.jwtUtil = jwtUtil;
	    }
	
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
		System.out.println("CustomForCookiePasswordAuthenticationFilter doFilter ....... ")	;
        // Example of using cookies without setAttribute
        Cookie cookie = new Cookie("jwt_token", jwtUtil.generateToken(SPRING_SECURITY_FORM_USERNAME_KEY));
        cookie.setMaxAge(3600);  // 1 hour expiration
        cookie.setPath("/");

        // Add the cookie to the response
         ((HttpServletResponse ) response).addCookie(cookie);

        // Continue with the filter chain
        chain.doFilter(request, response);
    }
  /*  @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Example of using cookies without setAttribute
        Cookie cookie = new Cookie("myToken", "someEncodedJwtToken");
        cookie.setMaxAge(3600);  // 1 hour expiration
        cookie.setPath("/");

        // Add the cookie to the response
        response.addCookie(cookie);

        // Continue with the filter chain
        chain.doFilter(request, response);
    }*/
}