package com.javalabs.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javalabs.dto.CreateProjectRequest;
import com.javalabs.dto.CreateProjectResponse;
import com.javalabs.exception.common.EntityNotFoundException;
import com.javalabs.model.Project;
import com.javalabs.repository.IProjectRepository;
import com.javalabs.service.IProjectService;

@Service
public class ProjectServiceImpl implements IProjectService {

	@Autowired
	private IProjectRepository projectRepository;
	
	@Override
	public CreateProjectResponse createProject(CreateProjectRequest projectRequest) {
		Project project = new Project();
		BeanUtils.copyProperties(projectRequest, project);
		projectRepository.save(project);
		CreateProjectResponse projectResponse = new CreateProjectResponse();
		BeanUtils.copyProperties(project, projectResponse);
		return projectResponse;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Project> findAll() {
		return projectRepository.findAll(); 
	}

	@Override
	@Transactional(readOnly = true)
	public Project findByProjectName(String name) {
		return projectRepository.findByName(name).orElseThrow(()-> new EntityNotFoundException("Project does not exist."));
	}

}
