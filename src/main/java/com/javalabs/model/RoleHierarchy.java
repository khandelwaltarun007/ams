package com.javalabs.model;

import java.util.Set;

import lombok.Data;

@Data
public class RoleHierarchy {
    private Role role;
    private Set<Role> childRoles;
    private Role parentRole;
}