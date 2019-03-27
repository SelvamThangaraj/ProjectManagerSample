package com.ts.pm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ts.pm.dao.TaskDAO;
import com.ts.pm.model.ParentTask;
import com.ts.pm.model.Task;

@Service
public class TaskServiceImpl implements TaskService {
	
	private final Logger LOGGER=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    TaskDAO taskDao;
		
	@Autowired
	ParentTaskService parentTaskService;
	
	public TaskServiceImpl() {
		
	}

	@Override
	public Task saveOrUpdateTask(Task task) {
		Task savedTask=taskDao.save(task);
		return savedTask;
	}

	@Override
	public Task getTask(long taskId) {
		Task task=taskDao.findById(taskId).orElse(null);
		return task;
	}

	@Override
	public long deleteTask(long taskId) {
		taskDao.deleteById(taskId);
		return taskId;
	}

	@Override
	public List<Task> getAllTasks() {
		Iterable<Task> iterableTask=taskDao.findAll();
		
		//Get map of parentTaskId,parentTaskTitle of ParentTasks
		Map<Long,String> mapParentTask=parentTaskService.getAllParentTasks();
		
		List<Task> listTask=new ArrayList<Task>();
		
		//add the ParentTaskTitle to the Task
		iterableTask.spliterator().forEachRemaining(
				task->{
					if(null!=task.getParentId()) {
						task.setParentTaskTitle(mapParentTask.get(task.getParentId()));
					}					
					listTask.add(task);			
		        }	
		);
		return listTask;
	}

	@Override
	public List<Task> sortByAttr(String attribute) {
		LOGGER.debug("Service task sortByAttr=>"+attribute);
		List<Task> taskList=getAllTasks();	
		LOGGER.debug("Service task sortByAttr TaskCount=>"+taskList.size());
		if("startDate".equalsIgnoreCase(attribute)) {			
			taskList.sort((Task u1,Task u2)->u1.getStartDate().compareTo(u2.getStartDate()));
		}else if("endDate".equalsIgnoreCase(attribute)) {
			taskList.sort((Task u1,Task u2)->u1.getEndDate().compareTo(u2.getEndDate()));
		}else if("priority".equalsIgnoreCase(attribute)) {
			taskList.sort((Task u1,Task u2)->u1.getPriority()-(u2.getPriority()));
		}else if("status".equalsIgnoreCase(attribute)) {
			taskList.sort((Task u1,Task u2)->u1.getStatus().compareTo(u2.getStatus()));
		}	
		LOGGER.debug("Service task sortByAttr sorted list=>"+taskList.toString());
		return taskList;
	}

	@Override
	public List<Task> getTasksByProjectid(Long projectId) {
		List <Task> listTask=taskDao.findByProjectId(projectId);
		
		//Get map of parentTaskId,parentTaskTitle of ParentTasks
		Map<Long,String> mapParentTask=parentTaskService.getAllParentTasks();
		
		listTask.stream().forEach(task->{
					if(null!=task.getParentId()) {
						task.setParentTaskTitle(mapParentTask.get(task.getParentId()));
					}								
		        }	
		);
		
		
		return listTask;
	}

	
	

}
