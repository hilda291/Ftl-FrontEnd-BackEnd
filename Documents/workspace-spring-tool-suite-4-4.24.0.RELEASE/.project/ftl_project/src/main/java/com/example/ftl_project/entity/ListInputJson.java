package com.example.ftl_project.entity;

public class ListInputJson {

	String name;
	String status;
	
	public ListInputJson(String name, String status) {
		super();
		this.name = name;
		this.status = status;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus_code() {
		return status;
	}
	public void setStatus_code(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ListInputJson [name=" + name + ", status_code=" + status + "]";
	}
	
}
