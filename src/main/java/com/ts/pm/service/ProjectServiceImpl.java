package com.ts.pm.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ts.pm.dao.ProjectDAO;
import com.ts.pm.model.Project;

@Service
public class ProjectServiceImpl implements ProjectService {
   
	private final Logger LOGGER=LoggerFactory.getLogger(this.getClass());
	@Autowired
    ProjectDAO projectDao;
	
	@Override
	public Project saveOrUpdateProject(Project project) {
		Project savedProject=projectDao.save(project);
		return savedProject;
	}

	@Override
	public Project getProject(long projectId) {
		Project project=projectDao.findById(projectId).orElse(null);
		return project;
	}

	@Override
	public long deleteProject(long projectId) {
		projectDao.deleteById(projectId);
		return projectId;
	}

	@Override
	public List<Project> getAllProjects() {
		Iterable<Project> iterableProject=projectDao.findAll();
		List<Project> listProject=new ArrayList<Project>();
		iterableProject.spliterator().forEachRemaining(p->listProject.add(p));
		return listProject;
	}

	@Override
	public List<Project> sortByAttr(String attribute) {
		LOGGER.debug("Service project sortByAttr=>"+attribute);
		List<Project> ProjectList=getAllProjects();	
		LOGGER.debug("Service project sortByAttr ProjectCount=>"+ProjectList.size());
		if("startDate".equalsIgnoreCase(attribute)) {			
			ProjectList.sort((Project u1,Project u2)->u1.getStartDate().compareTo(u2.getStartDate()));
		}else if("endDate".equalsIgnoreCase(attribute)) {
			ProjectList.sort((Project u1,Project u2)->u1.getEndDate().compareTo(u2.getEndDate()));
		}else if("priority".equalsIgnoreCase(attribute)) {
			ProjectList.sort((Project u1,Project u2)->u1.getPriority()-(u2.getPriority()));
		}	
		LOGGER.debug("Service project sortByAttr sorted list=>"+ProjectList.toString());
		return ProjectList;
	}

}
