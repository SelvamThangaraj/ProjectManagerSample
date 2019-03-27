package com.ts.pm.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ts.pm.model.ProjectWithTaskDetails;
import com.ts.pm.model.Task;
import com.ts.pm.service.ViewTaskService;

@RestController
public class ViewTaskController {
	
	@Autowired
	ViewTaskService viewTaskService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@GetMapping(path = "/viewtask/{projectId}" , produces = "application/json")
	public ResponseEntity<ProjectWithTaskDetails> getTasksByProjectId(@PathVariable("projectId") Long projectId) {
		LOGGER.debug("getTasksByProjectId projectId=>"+projectId);
		ProjectWithTaskDetails addedTask=viewTaskService.getProjectTaskDetails(projectId);		
        return new ResponseEntity<ProjectWithTaskDetails>(addedTask, HttpStatus.OK);		
	}
	
	
	@GetMapping(path = "/viewtask/{projectId}/sort/{attr}" ,produces = "application/json")
	public ResponseEntity<ProjectWithTaskDetails> sortByAttr(@PathVariable("projectId") Long projectId,
			                                     @PathVariable("attr") String attr) {
		LOGGER.debug("ViewTask sortByAttr=>"+attr);
		ProjectWithTaskDetails projectWithTaskDetails=viewTaskService.sortByAttr(projectId,attr);
		
		LOGGER.debug("projectWithTaskDetails=>"+projectWithTaskDetails);
		return new ResponseEntity<ProjectWithTaskDetails>(projectWithTaskDetails,HttpStatus.OK);
	}
}
