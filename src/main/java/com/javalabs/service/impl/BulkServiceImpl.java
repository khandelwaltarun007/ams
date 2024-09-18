package com.javalabs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javalabs.dto.CreateUserRequest;
import com.javalabs.dto.CreateUserResponse;
import com.javalabs.service.IBulkService;
import com.javalabs.service.IUserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BulkServiceImpl implements IBulkService {

	@Autowired
	private IUserService userService;
	
	@Override
	public List<CreateUserResponse> bulkUserCreation(List<CreateUserRequest> createUserRequests) {
		log.info("BulkServiceImpl :: bulkUserCreation :: entry");
		return createUserRequests.stream()
	            .map(userService::createUser)
	            .toList();
	}

}
