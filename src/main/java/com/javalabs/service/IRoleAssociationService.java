package com.javalabs.service;

import java.util.Set;

import com.javalabs.model.Role;

public interface IRoleAssociationService {
    void associateRoles(Long parentRoleId, Set<Long> childRoleIds);
    Set<Role> getAssociatedRoles(Long roleId);
}