package com.dell.mdp.interview.TaskManagerServer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dell.mdp.interview.TaskManagerServer.entity.UserInfo;


@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> { 
	 
	 
    //Optional<UserInfo> findByName(String username);
	
	public List<UserInfo> findByName(String username);
}
 