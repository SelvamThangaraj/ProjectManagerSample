package com.ts.pm.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ts.pm.dao.ParentTaskDAO;
import com.ts.pm.model.ParentTask;

@Service
public class ParentTaskServiceImpl implements ParentTaskService {

private final Logger LOGGER=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    ParentTaskDAO parentTaskDao;
	
	public ParentTaskServiceImpl() {}

	@Override
	public ParentTask saveOrUpdateParentTask(ParentTask task) {
		ParentTask parentTask=parentTaskDao.save(task);
		return parentTask;
	}

	@Override
	public ParentTask getParentTask(long parentTaskId) {
		ParentTask task=parentTaskDao.findById(parentTaskId).orElse(null);
		return task;
	}

	@Override
	public long deleteParentTask(long parentTaskId) {
		parentTaskDao.deleteById(parentTaskId);
		return parentTaskId;
	}

	@Override
	public List<ParentTask> getAllParentTasks() {
		Iterable<ParentTask> iterableTask=parentTaskDao.findAll();
		List<ParentTask> listTask=new ArrayList<ParentTask>();
		iterableTask.spliterator().forEachRemaining(task->listTask.add(task));;
		return listTask;
	}

	

}
