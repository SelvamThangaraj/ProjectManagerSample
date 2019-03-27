package com.ts.pm.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "Task")
public class Task implements Serializable {
	
	
	private static final long serialVersionUID = 4556537044094420320L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "TASK_SEQ")
	@SequenceGenerator(sequenceName = "task_seq", allocationSize = 1, name = "TASK_SEQ")
	@Column(name="task_id")
	Long taskId;
	
	@Column(name="parent_id")
	Long parentId;
	
	@Transient
	String parentTaskTitle;
		
    @Column(name="project_id")	
	Long projectId;
	
	@Column(name="task")
	String task;
	
	@Column(name="start_date")
	LocalDate startDate;
	
	@Column(name="end_date")
	LocalDate endDate;
	
	@Column(name="priority")
	int priority;
	
	@Column(name="status")
	String status;
	
	
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}



	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

	public String getParentTaskTitle() {
		return parentTaskTitle;
	}

	public void setParentTaskTitle(String parentTaskTitle) {
		this.parentTaskTitle = parentTaskTitle;
	}

	public Task() {	}
	
}
