package com.dell.mdp.interview.TaskManagerServer;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dell.mdp.interview.TaskManagerServer.entity.AuthRequest;
import com.dell.mdp.interview.TaskManagerServer.entity.UserInfo;
import com.dell.mdp.interview.TaskManagerServer.service.JwtService;
import com.dell.mdp.interview.TaskManagerServer.service.UserInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/auth") 
public class UserController { 

	  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	
  private final UserInfoService service; 
  private final JwtService jwtService; 
  private final AuthenticationManager authenticationManager; 
  UserController(UserInfoService service, JwtService jwtService, AuthenticationManager authenticationManager) { 
      this.service = service; 
      this.jwtService = jwtService; 
      this.authenticationManager = authenticationManager; 
  }

  @PostMapping("/register") 
  public ResponseEntity<String> addNewUser(@RequestBody UserInfo userInfo) { 
      String response = service.addUser(userInfo); 
      
     // Step 2: Generate JWT token for the user
      String token = jwtService.generateToken(userInfo.getName());
   // Convert map to JSON string
      String jsonString ="";
      try {
          // Create an ObjectMapper instance
          ObjectMapper objectMapper = new ObjectMapper();

          // Create a map to hold key-value pairs
          Map<String, String> map = new HashMap<>();
          map.put("username",userInfo.getName());
          map.put("token", token);

          // Convert map to JSON string
          jsonString = objectMapper.writeValueAsString(map);

          // Print the JSON string
          logger.info("This is user "+jsonString); 

      } catch (Exception e) {
         // e.printStackTrace();
          logger.info("This Exception message"+e.getMessage()); 
      }
      
      // Step 3: Return token and user details in response
      //return ResponseEntity.ok(new AuthResponse(token, user.getUsername()));
      return ResponseEntity.status(HttpStatus.CREATED).body(jsonString); 
  } 

  @PostMapping("/generateToken") 
  public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) { 
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())); 
      if (authentication.isAuthenticated()) { 
    	  logger.info("user authenticated ....."); 
          String token = jwtService.generateToken(authRequest.getUsername());
          return ResponseEntity.ok(token); 
      } else { 
    	  logger.info("user not present ....."); 
          throw new UsernameNotFoundException("Invalid user request!"); 
      } 
  } 
  @GetMapping("/hello")
  public String hello() {
      return "Hello World!";
  }

}