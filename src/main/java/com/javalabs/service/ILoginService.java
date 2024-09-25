package com.javalabs.service;

import com.javalabs.dto.LoginRequest;
import com.javalabs.dto.LoginResponse;

public interface ILoginService {

	LoginResponse authenticate(LoginRequest loginRequest) throws Exception;
}
