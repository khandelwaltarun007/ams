package com.javalabs.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javalabs.dto.CreateRoleRequest;
import com.javalabs.dto.CreateRoleResponse;
import com.javalabs.exception.common.EntityNotFoundException;
import com.javalabs.model.Role;
import com.javalabs.repository.IRoleRepository;
import com.javalabs.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;

    public Role getRoleHierarchy(Long roleId) {
        return roleRepository.findById(roleId).get();
    }

	@Override
	public CreateRoleResponse createRole(CreateRoleRequest createRoleRequest) {
		Role role = new Role();
		BeanUtils.copyProperties(createRoleRequest, role);
		CreateRoleResponse createRoleResponse = new CreateRoleResponse();
		roleRepository.save(role);
		BeanUtils.copyProperties(role, createRoleResponse);
		return createRoleResponse;
	}
	
	@Override
	public void associateRoles(Long childRoleId, Long parentRoleId) {
        Role childRole = roleRepository.findById(childRoleId).orElseThrow(() -> new IllegalArgumentException("Child role not found"));
        Role parentRole = roleRepository.findById(parentRoleId).orElseThrow(() -> new IllegalArgumentException("Parent role not found"));

        childRole.setParentRole(parentRole);
        if(parentRole.getChildRoles().isEmpty()) {
        	Set<Role> parentRoles = new HashSet<>();
        	parentRoles.add(childRole);
        	parentRole.setChildRoles(parentRoles);
        	roleRepository.save(parentRole);
        }else {
        	Set<Role> parentRoles = parentRole.getChildRoles();
            parentRoles.add(childRole);
            parentRole.setChildRoles(parentRoles);
            roleRepository.save(parentRole);
        }
        roleRepository.save(childRole);
    }
	
	@Transactional(readOnly = true)
	@Override
	public List<Role> getRoles(){
		return roleRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Role getRoleByName(String name) {
		return roleRepository.findByName(name).orElseThrow(()-> new EntityNotFoundException("Role does not exist."));
	}
}