package com.ts.pm.dao;

import org.springframework.data.repository.CrudRepository;

import com.ts.pm.model.Project;

public interface ProjectDAO extends CrudRepository<Project, Long> {

}
