package com.javalabs.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javalabs.dto.CreatePermissionRequest;
import com.javalabs.dto.CreatePermissionResponse;
import com.javalabs.exception.common.EntityNotFoundException;
import com.javalabs.model.Permission;
import com.javalabs.model.Role;
import com.javalabs.repository.IPermissionRepository;
import com.javalabs.repository.IRoleRepository;
import com.javalabs.service.IPermissionService;

//@Service
public class PermissionServiceImpl /*implements IPermissionService {

	@Autowired
	private IPermissionRepository permissionRepository;
	
	@Autowired
	private IRoleRepository roleRepository;
	
	
	@Override
	public CreatePermissionResponse createPermission(CreatePermissionRequest createPermissionRequest) {
		Permission permission = new Permission();
		BeanUtils.copyProperties(createPermissionRequest, permission);
		Permission createdPermission = permissionRepository.save(permission);
		CreatePermissionResponse permissionResponse = new CreatePermissionResponse();
		BeanUtils.copyProperties(createdPermission, permissionResponse);
		return permissionResponse;
	}

	@Override
	public List<Permission> getPermissions() {
		return permissionRepository.findAll();
	}

	@Override
	public void deletePermission(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void associatePermission(Long roleId, Long permissionId) {
		Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new EntityNotFoundException("Permission does not exist with id: " + permissionId));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleId));
        List<Permission> registeredPermissions = role.getPermissions();
        registeredPermissions.add(permission);
        role.setPermissions(registeredPermissions);
        roleRepository.save(role);

	}*/ {

}
