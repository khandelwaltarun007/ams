package com.javalabs.service;

import java.util.List;

import com.javalabs.dto.CreateProjectRequest;
import com.javalabs.dto.CreateProjectResponse;
import com.javalabs.model.Project;

public interface IProjectService {

	CreateProjectResponse createProject(CreateProjectRequest projectRequest);
	
	List<Project> findAll();
	
	Project findByProjectName(String name);
}
