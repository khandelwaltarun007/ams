package com.javalabs.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javalabs.dto.CreateProjectRequest;
import com.javalabs.dto.CreateProjectResponse;
import com.javalabs.model.Project;
import com.javalabs.service.IProjectService;

@RestController
public class ProjectResource {

	@Autowired
	private IProjectService projectService;
	
	@PostMapping("/project")
	public ResponseEntity<CreateProjectResponse> createProject(@RequestBody CreateProjectRequest projectRequest){
		return new ResponseEntity<>(projectService.createProject(projectRequest), HttpStatus.CREATED);
	}
	
	@GetMapping("/project")
	public ResponseEntity<List<Project>> getAllUser(){
		return ResponseEntity.ok(projectService.findAll());
	}
	
	@GetMapping("/project/{name}")
	public ResponseEntity<Project> getUserByUsername(@PathVariable("name") String username){
		return ResponseEntity.ok(projectService.findByProjectName(username));
	}
}
