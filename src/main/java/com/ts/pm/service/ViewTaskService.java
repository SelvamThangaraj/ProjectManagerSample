package com.ts.pm.service;

import com.ts.pm.model.ProjectWithTaskDetails;

public interface ViewTaskService {
	
	ProjectWithTaskDetails getProjectTaskDetails(long projectId);
	ProjectWithTaskDetails sortByAttr(long projectId, String attribute);

}
