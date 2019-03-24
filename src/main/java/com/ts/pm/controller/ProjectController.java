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

import com.ts.pm.model.Project;
import com.ts.pm.service.ProjectService;

@RestController
public class ProjectController {
	
	@Autowired
	ProjectService projectService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@PostMapping(path = "/project" ,consumes = "application/json", produces = "application/json")
	public ResponseEntity<Project> addProject(@RequestBody Project project, UriComponentsBuilder builder) {
		LOGGER.debug("project=>"+project);
		Project addedProject=projectService.saveOrUpdateProject(project);
		if(null==addedProject) {
			return new ResponseEntity<Project>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/project/{id}").buildAndExpand(project.getProjectId()).toUri());
        return new ResponseEntity<Project>(addedProject,headers, HttpStatus.CREATED);		
	}
	
	@PutMapping(path = "/project" ,consumes = "application/json", produces = "application/json")
	public ResponseEntity<Project> updateProject(@RequestBody Project project) {
		LOGGER.debug("project=>"+project);
		Project addedProject=projectService.saveOrUpdateProject(project);
		return new ResponseEntity<Project>(addedProject,HttpStatus.OK);
	}
	
	@GetMapping(path = "/project/{id}" ,produces = "application/json")
	public ResponseEntity<Project> getProject(@PathVariable("id") Long id) {
		LOGGER.debug("id=>"+id);
		Project addedProject=projectService.getProject(id);
		return new ResponseEntity<Project>(addedProject,HttpStatus.OK);
	}
	
	@GetMapping(path = "/projects" ,produces = "application/json")
	public ResponseEntity<List<Project>> getAllProjects() {		
		List<Project> listProjects=projectService.getAllProjects();
		LOGGER.debug("listProjects=>"+listProjects);
		return new ResponseEntity<List<Project>>(listProjects,HttpStatus.OK);
	}
	
	@DeleteMapping("/project/{id}")
	public ResponseEntity<Long> deleteProject(@PathVariable("id") Long id) {
		LOGGER.debug("Delete id=>"+id);
		Long deletedProjectid=projectService.deleteProject(id);
		return new ResponseEntity<Long>(deletedProjectid,HttpStatus.NO_CONTENT);
	}	
	
	@GetMapping(path = "/projects/sort/{attr}" ,produces = "application/json")
	public ResponseEntity<List<Project>> sortByAttr(@PathVariable("attr") String attr) {		
		List<Project> listProjects=projectService.sortByAttr(attr);
		LOGGER.debug("listProjects=>"+listProjects);
		return new ResponseEntity<List<Project>>(listProjects,HttpStatus.OK);
	}
}
