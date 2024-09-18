package com.javalabs.service;

import java.util.List;

import com.javalabs.dto.CreateUserRequest;
import com.javalabs.dto.CreateUserResponse;

public interface IBulkService {
	
	public List<CreateUserResponse> bulkUserCreation(List<CreateUserRequest> createUserRequests);

}
