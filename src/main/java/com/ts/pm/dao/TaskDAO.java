package com.ts.pm.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ts.pm.model.Task;
@Repository
public interface TaskDAO extends CrudRepository<Task, Long> {
	
	public List<Task> findByProjectId(Long projectId);

}
