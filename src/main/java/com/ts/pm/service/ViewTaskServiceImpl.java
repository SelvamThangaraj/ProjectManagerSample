package com.ts.pm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ts.pm.dao.ParentTaskDAO;
import com.ts.pm.dao.ProjectDAO;
import com.ts.pm.dao.TaskDAO;
import com.ts.pm.model.ParentTask;
import com.ts.pm.model.Project;
import com.ts.pm.model.ProjectWithTaskDetails;
import com.ts.pm.model.Task;

@Service
public class ViewTaskServiceImpl implements ViewTaskService {
	
	@Autowired
    TaskDAO taskDao;
	
	@Autowired
	ParentTaskDAO parentTaskDao;
	
	@Autowired
	ProjectDAO projectDao;
	
	@Autowired
	TaskService taskService;
	

	@Override
	public ProjectWithTaskDetails getProjectTaskDetails(long projectId) {
		
		ProjectWithTaskDetails projWithTaskDetails=new ProjectWithTaskDetails();
		// Find the project details
		Project project=projectDao.findById(projectId).orElse(null);
		projWithTaskDetails.setProject(project);
		
		// Find the list of task related to project id
		List <Task> listTask=taskService.getTasksByProjectid(projectId);				
		projWithTaskDetails.setListTask(listTask);		
		
		return projWithTaskDetails;
	}

	@Override
	public ProjectWithTaskDetails sortByAttr(long projectId, String attribute) {		
		ProjectWithTaskDetails projWithTaskDetails=getProjectTaskDetails(projectId);
		projWithTaskDetails.setListTask(taskService.sortByAttr(attribute));		
		return projWithTaskDetails;
	}
	
	

}
