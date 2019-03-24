package com.ts.pm.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="project")
public class Project {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "PROJ_SEQ")
	@SequenceGenerator(sequenceName = "project_seq", allocationSize = 1, name = "PROJ_SEQ")
	@Column(name="project_id")
	Long projectId;
	
	@Column(name="project_title")
	String projectTitle;
	
	
	@Column(name="start_date")
	//@Convert(converter = LocalDateConverter.class)	
	LocalDate startDate;
	
	@Column(name="end_date")
	//@Convert(converter = LocalDateConverter.class)	
	LocalDate endDate;
	
	@Column(name="priority")
	int priority;

	

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Project(long projectID, String projectTitle, LocalDate startDate, LocalDate endDate, int priority) {
		super();
		this.projectId = projectID;
		this.projectTitle = projectTitle;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priority = priority;
	}

	public Project() {
		super();		
	}

	@Override
	public String toString() {
		return "Project [projectID=" + projectId + ", projectTitle=" + projectTitle + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", priority=" + priority + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (projectId ^ (projectId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (projectId != other.projectId)
			return false;
		return true;
	}
	
	
	
	
	
	

}
