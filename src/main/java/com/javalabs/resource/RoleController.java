package com.javalabs.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javalabs.dto.CreateRoleRequest;
import com.javalabs.dto.CreateRoleResponse;
import com.javalabs.model.Role;
import com.javalabs.service.IRoleService;

@RestController
public class RoleController {
    @Autowired
    private IRoleService roleService;

	/*
	 * @GetMapping("/roles/{id}/hierarchy") public ResponseEntity<Role>
	 * getRoleHierarchy(@PathVariable("id") Long roleId) { return
	 * ResponseEntity.ok(roleService.getRoleHierarchy(roleId)); }
	 */
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok(roleService.getRoles());
    }
    
    @GetMapping("/roles/{name}")
    public ResponseEntity<Role> getRoleByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(roleService.getRoleByName(name));
    }
    
    @PostMapping("/roles")
    public ResponseEntity<CreateRoleResponse> createRole(@RequestBody CreateRoleRequest createRoleRequest){
    	return new ResponseEntity<>(roleService.createRole(createRoleRequest),HttpStatus.CREATED);
    }
    
}