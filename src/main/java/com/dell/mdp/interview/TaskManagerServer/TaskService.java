package com.dell.mdp.interview.TaskManagerServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;


    /**
     * Constructor for injecting the TaskRepository dependency.
     *
     * @param taskRepository the repository to interact with the database
     */
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Creates a new task in the database.
     *
     * @param task the task object to create
     * @return the created task
     */
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    /**
     * Retrieves all tasks from the database.
     *
     * @return a list of all tasks
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id the ID of the task to retrieve
     * @return an Optional containing the task if found, otherwise an empty Optional
     */
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    /**
     * Updates an existing task by its ID.
     *
     * @param id   the ID of the task to update
     * @param task the updated task object
     * @return the updated task, or null if the task does not exist
     */
    public Task updateTask(Long id, Task task) {
        if (taskRepository.existsById(id)) {
            task.setId(id);
            return taskRepository.save(task);
        }
        return null;
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to delete
     * @return true if the task was successfully deleted, false if not found
     */
    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}