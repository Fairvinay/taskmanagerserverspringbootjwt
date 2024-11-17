package com.dell.mdp.interview.TaskManagerServer;

 
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing Task entities from the database.
 * Extends JpaRepository for CRUD operations and additional query methods.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
	 // JpaRepository already provides methods for CRUD operations (save, findById, findAll, deleteById).

}