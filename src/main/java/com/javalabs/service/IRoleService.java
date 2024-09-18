package com.javalabs.service;

import java.util.List;

import com.javalabs.dto.CreateRoleRequest;
import com.javalabs.dto.CreateRoleResponse;
import com.javalabs.model.Role;

public interface IRoleService {
	public Role getRoleHierarchy(Long roleId);

	public CreateRoleResponse createRole(CreateRoleRequest createRoleRequest);
	public void associateRoles(Long childRoleId, Long parentRoleId);
	public List<Role> getRoles();
	public Role getRoleByName(String name);

}
