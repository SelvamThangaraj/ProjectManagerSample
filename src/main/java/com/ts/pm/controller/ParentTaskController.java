package com.ts.pm.controller;

import java.util.List;
import java.util.Map;

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

import com.ts.pm.model.ParentTask;
import com.ts.pm.service.ParentTaskService;

@RestController
public class ParentTaskController {
	
	@Autowired
	ParentTaskService taskService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@PostMapping(path = "/parenttask" ,consumes = "application/json", produces = "application/json")
	public ResponseEntity<ParentTask> addTask(@RequestBody ParentTask task, UriComponentsBuilder builder) {
		LOGGER.debug("task=>"+task);
		ParentTask addedTask=taskService.saveOrUpdateParentTask(task);
		if(null==addedTask) {
			return new ResponseEntity<ParentTask>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/task/{id}").buildAndExpand(task.getParentId()).toUri());
        return new ResponseEntity<ParentTask>(addedTask,headers, HttpStatus.CREATED);		
	}
	
	@PutMapping(path = "/parenttask" ,consumes = "application/json", produces = "application/json")
	public ResponseEntity<ParentTask> updateTask(@RequestBody ParentTask task) {
		LOGGER.debug("ParentTask=>"+task);
		ParentTask addedTask=taskService.saveOrUpdateParentTask(task);
		return new ResponseEntity<ParentTask>(addedTask,HttpStatus.OK);
	}
	
	@GetMapping(path = "/parenttask/{id}" ,produces = "application/json")
	public ResponseEntity<ParentTask> getParentTask(@PathVariable("id") Long id) {
		LOGGER.debug("id=>"+id);
		ParentTask addedTask=taskService.getParentTask(id);
		return new ResponseEntity<ParentTask>(addedTask,HttpStatus.OK);
	}
	
	@GetMapping(path = "/parenttasks" ,produces = "application/json")
	public ResponseEntity<Map<Long,String>> getAllParentTasks() {		
		Map<Long,String> mapParentTasks=taskService.getAllParentTasks();
		LOGGER.debug("mapParentTasks=>"+mapParentTasks);
		return new ResponseEntity<Map<Long,String>>(mapParentTasks,HttpStatus.OK);
	}
	
	@DeleteMapping("/parenttask/{id}")
	public ResponseEntity<Long> deleteTask(@PathVariable("id") Long id) {
		LOGGER.debug("Delete id=>"+id);
		Long deletedTaskid=taskService.deleteParentTask(id);
		return new ResponseEntity<Long>(deletedTaskid,HttpStatus.NO_CONTENT);
	}		
	
}
