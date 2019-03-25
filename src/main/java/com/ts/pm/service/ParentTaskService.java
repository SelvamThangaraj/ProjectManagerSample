package com.ts.pm.service;

import java.util.List;

import com.ts.pm.model.ParentTask;

public interface ParentTaskService {
	
	ParentTask saveOrUpdateParentTask(ParentTask task);
	ParentTask getParentTask(long parentTaskId);	
	long deleteParentTask(long parentTaskId);
	List<ParentTask> getAllParentTasks();	

}
