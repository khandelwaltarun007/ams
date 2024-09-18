package com.javalabs.service;

import com.javalabs.dto.LoginRequest;

public interface ILoginService {

	boolean authenticate(LoginRequest loginRequest);
}
