package com.dell.mdp.interview.TaskManagerServer;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String task;
    private boolean completed;
    private String title;
    private String description;
    private LocalDateTime dueDate;

    // Getters and Setters

    public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

	@Override
	public String toString() {
		return "{ \"id\":\"" + id +  "\" \"task\":\"" +task + "\", \"completed\":"+completed+ 
				  "\", \"title\":\"" + title +
				"\", \"description\": \"" + description + 
				"\", \"dueDate\":\"" + dueDate + "\"}";
	}
}