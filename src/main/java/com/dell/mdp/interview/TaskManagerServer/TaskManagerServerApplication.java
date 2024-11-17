package com.dell.mdp.interview.TaskManagerServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
/*@EntityScan(basePackages = "com.dell.mdp.interview.TaskManagerServer.entity")  // Scan JPA entities
@EnableJpaRepositories(basePackages = "com.dell.mdp.interview.TaskManagerServer.repository")  // Scan JPA repositories
@ComponentScan(basePackages = {"com.dell.mdp.interview.TaskManagerServer.*","com.dell.mdp.interview.TaskManagerServer.service",
		"com.dell.mdp.interview.TaskManagerServer.entity", 
		"com.dell.mdp.interview.TaskManagerServer.repository",
		"com.dell.mdp.interview.TaskManagerServer.filter"})*/

public class TaskManagerServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerServerApplication.class, args);
	}

}
