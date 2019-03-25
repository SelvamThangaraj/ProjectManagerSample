package com.ts.pm.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ts.pm.model.Task;
import com.ts.pm.service.TaskService;

@RestController
public class TaskController {
	
	@Autowired
	TaskService taskService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@PostMapping(path = "/task" ,consumes = "application/json", produces = "application/json")
	public ResponseEntity<Task> addTask(@RequestBody Task task, UriComponentsBuilder builder) {
		LOGGER.debug("task=>"+task);
		Task addedTask=taskService.saveOrUpdateTask(task);
		if(null==addedTask) {
			return new ResponseEntity<Task>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/task/{id}").buildAndExpand(task.getTaskId()).toUri());
        return new ResponseEntity<Task>(addedTask,headers, HttpStatus.CREATED);		
	}
	
	@PutMapping(path = "/task" ,consumes = "application/json", produces = "application/json")
	public ResponseEntity<Task> updateTask(@RequestBody Task task) {
		LOGGER.debug("task=>"+task);
		Task addedTask=taskService.saveOrUpdateTask(task);
		return new ResponseEntity<Task>(addedTask,HttpStatus.OK);
	}
	
	@GetMapping(path = "/task/{id}" ,produces = "application/json")
	public ResponseEntity<Task> getTask(@PathVariable("id") Long id) {
		LOGGER.debug("id=>"+id);
		Task addedTask=taskService.getTask(id);
		return new ResponseEntity<Task>(addedTask,HttpStatus.OK);
	}
	
	@GetMapping(path = "/tasks" ,produces = "application/json")
	public ResponseEntity<List<Task>> getAllTasks() {		
		List<Task> listTasks=taskService.getAllTasks();
		LOGGER.debug("listTasks=>"+listTasks);
		return new ResponseEntity<List<Task>>(listTasks,HttpStatus.OK);
	}
	
	@DeleteMapping("/task/{id}")
	public ResponseEntity<Long> deleteTask(@PathVariable("id") Long id) {
		LOGGER.debug("Delete id=>"+id);
		Long deletedTaskid=taskService.deleteTask(id);
		return new ResponseEntity<Long>(deletedTaskid,HttpStatus.NO_CONTENT);
	}	
	
	@GetMapping(path = "/tasks/sort/{attr}" ,produces = "application/json")
	public ResponseEntity<List<Task>> sortByAttr(@PathVariable("attr") String attr) {		
		List<Task> listTasks=taskService.sortByAttr(attr);
		LOGGER.debug("listTasks=>"+listTasks);
		return new ResponseEntity<List<Task>>(listTasks,HttpStatus.OK);
	}
}
