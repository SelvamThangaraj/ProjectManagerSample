package com.ts.pm.model;

import java.util.List;

public class ProjectWithTaskDetails {
	
	Project project;	
	List<Task> listTask;
	
		
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public List<Task> getListTask() {
		return listTask;
	}
	public void setListTask(List<Task> listTask) {
		this.listTask = listTask;
	}	

	
}
