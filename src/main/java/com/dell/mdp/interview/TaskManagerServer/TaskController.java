package com.dell.mdp.interview.TaskManagerServer;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:4200",  allowedHeaders = "Content-Type, Authorization" ) // Allowed headers (can be comma-separated))
public class TaskController {
	
	@Autowired
	private  TaskService taskService;

	 private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    
    /**
     * Creates a new task.
     *
     * @param task the task object to create
     * @return a ResponseEntity with the created task and HTTP status code 201 (Created)
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
    	 
    	 logger.info("createTask ...... "+task.toString()); 
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }
  
    /**
     * Retrieves all tasks.
     *
     * @return a list of all tasks
     */
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id the ID of the task to retrieve
     * @return a ResponseEntity with the task if found, or HTTP status 404 (Not Found) if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        if(task.isPresent())
         logger.info("createTask ...... "+task.get()); 
        
        return task.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing task by its ID.
     *
     * @param id   the ID of the task to update
     * @param task the updated task object
     * @return a ResponseEntity with the updated task, or HTTP status 404 (Not Found) if task doesn't exist
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        return updatedTask != null ? ResponseEntity.ok(updatedTask) : ResponseEntity.notFound().build();
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to delete
     * @return a ResponseEntity with HTTP status 204 (No Content) if deleted, or 404 (Not Found) if task doesn't exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        boolean deleted = taskService.deleteTask(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
	
	
}