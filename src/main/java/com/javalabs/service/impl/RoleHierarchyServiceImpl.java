package com.javalabs.service.impl;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javalabs.model.Role;
import com.javalabs.model.RoleHierarchy;
import com.javalabs.repository.IRoleRepository;
import com.javalabs.service.IRoleHierarchyService;

@Service
public class RoleHierarchyServiceImpl implements IRoleHierarchyService {

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public RoleHierarchy getRoleHierarchy(Long roleId) {
        Role role = roleRepository.findById(roleId)
                                  .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        List<Role> childRoles = roleRepository.findByParentRole_Id(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        System.out.println(childRoles.toString());
        RoleHierarchy roleHierarchy = new RoleHierarchy();
        roleHierarchy.setRole(role);
        roleHierarchy.setChildRoles(new HashSet<>(childRoles));
        roleHierarchy.setParentRole(role.getParentRole());
        System.out.println("##########"+role.toString());
        return roleHierarchy;
    }
}