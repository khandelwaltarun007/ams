package com.javalabs.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.javalabs.model.RoleHierarchy;
import com.javalabs.service.IRoleHierarchyService;

@RestController
public class RoleHierarchyController {

    @Autowired
    private IRoleHierarchyService roleHierarchyService;

    @GetMapping("/roles/{id}/hierarchy")
    public ResponseEntity<RoleHierarchy> getRoleHierarchy(@PathVariable("id") Long roleId) {
        RoleHierarchy roleHierarchy = roleHierarchyService.getRoleHierarchy(roleId);
        return ResponseEntity.ok(roleHierarchy);
    }
}