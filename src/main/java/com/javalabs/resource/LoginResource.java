package com.javalabs.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javalabs.dto.LoginRequest;
import com.javalabs.dto.LoginResponse;
import com.javalabs.service.ILoginService;

@RestController
@CrossOrigin("*")
public class LoginResource {

	@Autowired
	private ILoginService loginService;
	
	@PostMapping("/auth")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest login) throws Exception{
		System.out.println(login.toString());
		return ResponseEntity.ok(loginService.authenticate(login));
	}
}
