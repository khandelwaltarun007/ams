package com.javalabs.resource;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javalabs.model.Role;
import com.javalabs.service.IRoleAssociationService;

@RestController
public class RoleAssociationController {

    @Autowired
    private IRoleAssociationService roleAssociationService;

    @PostMapping("/roles/{parentId}/associate")
    public ResponseEntity<String> associateRoles(@PathVariable("parentId") Long parentId,
                                                 @RequestBody Set<Long> childIds) {
        roleAssociationService.associateRoles(parentId, childIds);
        return ResponseEntity.ok("Roles associated successfully");
    }

    @GetMapping("/roles/{roleId}/associated")
    public ResponseEntity<Set<Role>> getAssociatedRoles(@PathVariable("roleId") Long roleId) {
        Set<Role> associatedRoles = roleAssociationService.getAssociatedRoles(roleId);
        return ResponseEntity.ok(associatedRoles);
    }
}