package com.javalabs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.javalabs.config.jwt.CustomUserDetailsService;
import com.javalabs.config.jwt.JwtUtil;
import com.javalabs.dto.LoginRequest;
import com.javalabs.dto.LoginResponse;
import com.javalabs.exception.common.AuthenticationException;
import com.javalabs.service.ILoginService;

@Service
public class LoginServiceImpl implements ILoginService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService userDetailService;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public LoginResponse authenticate(LoginRequest loginRequest) {
		String username = loginRequest.getUsername();
		String password = loginRequest.getPassword();
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException e) {
			throw new AuthenticationException("Invalid username or password.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		final UserDetails userDetails = userDetailService.loadUserByUsername(username);
		final String token = jwtUtil.generateToken(userDetails);
		//System.out.println(userCustomRepository.getUserByUsername(jwtUtil.extractUserName(token)));
		return new LoginResponse(token, jwtUtil.getTokenExpirationDate(token));
	}

}
