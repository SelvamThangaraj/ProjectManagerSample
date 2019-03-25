package com.ts.pm.service;

import java.util.List;

import com.ts.pm.model.Task;

public interface TaskService {
	
	Task saveOrUpdateTask(Task task);
	Task getTask(long taskId);	
	long deleteTask(long taskId);
	List<Task> getAllTasks();
	List<Task> sortByAttr(String attribute);

}
