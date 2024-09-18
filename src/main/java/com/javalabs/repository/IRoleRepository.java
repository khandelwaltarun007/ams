package com.javalabs.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javalabs.model.Role;

public interface IRoleRepository extends JpaRepository<Role, Long> {
	List<Role> findByIdIn(Set<Long> ids);

	Optional<List<Role>> findByParentRole_Id(Long roleId);
	
	Optional<Role> findByName(String roleName);
}