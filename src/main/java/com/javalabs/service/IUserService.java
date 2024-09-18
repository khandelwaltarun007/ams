package com.javalabs.service;

import java.util.List;

import com.javalabs.dto.CreateUserRequest;
import com.javalabs.dto.CreateUserResponse;
import com.javalabs.dto.GetUserResponse;
import com.javalabs.model.User;

public interface IUserService {
	CreateUserResponse createUser(CreateUserRequest createRequest);
	GetUserResponse findByUserId(Long userId);
	List<GetUserResponse> findAll();
	void updatePassword(String username, String password);
	void enableUserByUsername(String username);
	GetUserResponse findByUsername(String username);
	void associateManager(Long userId, Long managerId);
	User findUserByUserId(Long userId);
	void associateRole(Long userId, Long roleId);
}
