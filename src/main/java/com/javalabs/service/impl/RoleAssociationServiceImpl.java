package com.javalabs.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javalabs.model.Role;
import com.javalabs.repository.IRoleRepository;
import com.javalabs.service.IRoleAssociationService;

@Service
public class RoleAssociationServiceImpl implements IRoleAssociationService {

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public void associateRoles(Long parentRoleId, Set<Long> childRoleIds) {
        Role parentRole = roleRepository.findById(parentRoleId)
                                        .orElseThrow(() -> new IllegalArgumentException("Parent Role not found"));

        List<Role> childRoles = roleRepository.findAllById(childRoleIds);

        // Update the parent role for each child role
        for (Role childRole : childRoles) {
            childRole.setParentRole(parentRole);
            roleRepository.save(childRole);
        }
    }

    @Override
    public Set<Role> getAssociatedRoles(Long roleId) {
        Role role = roleRepository.findById(roleId)
                                  .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        return role.getChildRoles();
    }
}