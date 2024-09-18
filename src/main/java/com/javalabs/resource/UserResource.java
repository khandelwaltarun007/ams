package com.javalabs.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.javalabs.bulk.ImportBulkUsers;
import com.javalabs.dto.CreateUserRequest;
import com.javalabs.dto.CreateUserResponse;
import com.javalabs.dto.GetUserResponse;
import com.javalabs.service.IBulkService;
import com.javalabs.service.IUserService;

@RestController
@CrossOrigin
public class UserResource {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IBulkService bulkService;
	
	@PostMapping("/user")
	public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest userCreateRequest){
		return new ResponseEntity<>(userService.createUser(userCreateRequest),HttpStatus.CREATED);
	}
	
	@PostMapping("/users")
	public ResponseEntity<List<CreateUserResponse>> createUsers(@RequestParam("file") MultipartFile file){
		if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
		List<CreateUserRequest> createUserRequests = ImportBulkUsers.importBulkUsersFromExcel();
		return new ResponseEntity<>(bulkService.bulkUserCreation(createUserRequests),HttpStatus.CREATED);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<GetUserResponse>> getAllUser(){
		return ResponseEntity.ok(userService.findAll());
	}
	
	@GetMapping("/user/{username}")
	public ResponseEntity<GetUserResponse> getUserByUsername(@PathVariable("username") String username){
		return ResponseEntity.ok(userService.findByUsername(username));
	}
	
	@PatchMapping("/user/{userId}/manager/{managerId}")
    public ResponseEntity<String> patchUserManager(@PathVariable Long userId, @PathVariable Long managerId) {
        userService.associateManager(userId, managerId);
        return ResponseEntity.ok("Manager has been associated.");
    }
	
	@PatchMapping("/user/{userId}/role/{roleId}")
    public ResponseEntity<String> patchUserRole(@PathVariable Long userId, @PathVariable Long roleId) {
        userService.associateRole(userId, roleId);
        return ResponseEntity.ok("Role has been associated.");
    }
}
