package com.javalabs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javalabs.dto.LoginRequest;
import com.javalabs.exception.common.AuthenticationException;
import com.javalabs.exception.common.EntityNotFoundException;
import com.javalabs.model.User;
import com.javalabs.repository.IUserRepository;
import com.javalabs.service.ILoginService;
import com.javalabs.util.CodeUtility;

@Service
public class LoginServiceImpl implements ILoginService {

	@Autowired
	private IUserRepository userRepository;

	@Override
	public boolean authenticate(LoginRequest loginRequest) {
		User user = userRepository.findByUsername(loginRequest.getUsername())
				.orElseThrow(() -> new EntityNotFoundException("User does not exist."));
		if(!user.isStatus()) {
			throw new AuthenticationException("User is inactive.");
		}
		if(user.isTemporaryPassword()) {
			throw new AuthenticationException("Please set the permanent password.");
		}
		String encodedPassword = new String(CodeUtility.encodePassword(loginRequest.getPassword()));
		if (!(user.getPassword()
				.equals(encodedPassword))) {
			throw new AuthenticationException("Username/Password is incorrect.");
		}
		return true;
	}

}
