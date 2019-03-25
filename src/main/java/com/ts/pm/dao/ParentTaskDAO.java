package com.ts.pm.dao;

import org.springframework.data.repository.CrudRepository;

import com.ts.pm.model.ParentTask;

public interface ParentTaskDAO extends CrudRepository<ParentTask, Long> {

}
