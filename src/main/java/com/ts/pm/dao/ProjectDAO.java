package com.ts.pm.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ts.pm.model.Project;
@Repository
public interface ProjectDAO extends CrudRepository<Project, Long> {

}
