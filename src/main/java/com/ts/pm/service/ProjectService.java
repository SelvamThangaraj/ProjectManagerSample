package com.ts.pm.service;

import java.util.List;

import com.ts.pm.model.Project;

public interface ProjectService {
	
	Project saveOrUpdateProject(Project project);
	Project getProject(long projectId);	
	long deleteProject(long projectId);
	List<Project> getAllProjects();
	List<Project> sortByAttr(String attribute);

}
