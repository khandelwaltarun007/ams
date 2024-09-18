package com.javalabs.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javalabs.model.Project;

@Repository
public interface IProjectRepository extends JpaRepository<Project, Long> {

	Optional<Project> findByName(String project);
	Set<Project> findByNameIn(List<String> projectNames);

}
