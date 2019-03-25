package com.ts.pm.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="parent_task")
public class ParentTask implements Serializable{
	
	private static final long serialVersionUID = -2507206004440094892L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "PARENT_TASK_ID_SEQ")
	@SequenceGenerator(sequenceName = "project_task_id_seq", allocationSize = 1, name = "PARENT_TASK_ID_SEQ")
	@Column(name="parent_id")
	Long parentId;
	
	@Column(name="parent_task")
	String parentTask;

	/*@OneToMany( targetEntity=Task.class )
	@JoinColumn
	List<Task> listTask;*/
	
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentTask() {
		return parentTask;
	}

	public void setParentTask(String parentTask) {
		this.parentTask = parentTask;
	}
	
	/*public List<Task> getListTask() {
		return listTask;
	}

	public void setListTask(List<Task> listTask) {
		this.listTask = listTask;
	}*/

	public ParentTask(Long parentId, String parentTask) {
		super();
		this.parentId = parentId;
		this.parentTask = parentTask;
	}

	public ParentTask() {
		super();		
	}

	@Override
	public String toString() {
		return "ParentTask [parentId=" + parentId + ", parentTask=" + parentTask + "]";
	}	

}
