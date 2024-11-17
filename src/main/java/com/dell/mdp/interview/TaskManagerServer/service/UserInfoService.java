package com.dell.mdp.interview.TaskManagerServer.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dell.mdp.interview.TaskManagerServer.entity.UserInfo;
import com.dell.mdp.interview.TaskManagerServer.repository.UserInfoRepository;

@Service
public class UserInfoService implements UserDetailsService {
   
	  private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);
	
	private final UserInfoRepository repository; 
    private final PasswordEncoder encoder;
    public UserInfoService(UserInfoRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }
    
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		   logger.info("loadUserByUsername   .....   "); 
		 List<UserInfo> users = repository.findByName(username);
		 
		    if (users.isEmpty()) {
		    	logger.info("loadUserByUsername   no user found .....   "); 
		        throw new UsernameNotFoundException("User not found with username: " + username);
		    }/* else if (users.size() > 1) {
		        // Handle the case where there are multiple users
		        // You could throw a custom exception or log the issue
				
				 * throw new RuntimeException("Multiple users found with username: " +
				 * username); } else {
				
		    	   UserInfo  userinfo =users.get(0);
		    	   Optional<UserInfo> userDetail = Optional.of(userinfo);
		    	 
		         return userDetail.map(UserInfoDetails::new) 
		     		    .orElseThrow(() -> new UsernameNotFoundException("User not found " + username)); 
		    } */
		    else { 
		    	logger.info("loadUserByUsername  returning first found user  .....   "); 
		    	UserInfo  userinfo =users.get(0);
		    	   Optional<UserInfo> userDetail = Optional.of(userinfo);
		    	 
		         return userDetail.map(UserInfoDetails::new) 
		     		    .orElseThrow(() -> new UsernameNotFoundException("User not found " + username)); 
		    }
		 // Optional<UserInfo> userDetail = repository.findByName(username); 
		
		
		  // Converting userDetail to UserDetails 
		 // return userDetail.map(UserInfoDetails::new) 
		  //   .orElseThrow(() -> new UsernameNotFoundException("User not found " + username)); 
		 } 
		 public String addUser(UserInfo userInfo) { 
		  userInfo.setPassword(encoder.encode(userInfo.getPassword())); 
		  repository.save(userInfo); 
		  return "User Added Successfully"; 
		 } 

}
