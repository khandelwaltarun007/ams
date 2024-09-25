package com.javalabs.service;

import java.util.List;

import com.javalabs.dto.CreatePermissionRequest;
import com.javalabs.dto.CreatePermissionResponse;
import com.javalabs.model.Permission;

public interface IPermissionService {
	
	public CreatePermissionResponse createPermission(CreatePermissionRequest createPermissionRequest);
	public List<Permission> getPermissions();
	public void deletePermission(Long id);
	void associatePermission(Long roleId, Long permissionId);

}
