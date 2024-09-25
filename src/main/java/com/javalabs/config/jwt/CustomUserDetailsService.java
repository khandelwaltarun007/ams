package com.javalabs.config.jwt;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.javalabs.exception.common.AuthenticationException;
import com.javalabs.exception.common.EntityNotFoundException;
import com.javalabs.model.Role;
import com.javalabs.model.User;
import com.javalabs.repository.IUserRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserRepository userRepository;

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new EntityNotFoundException("User does not exist."));
		if(!user.isStatus()) {
			throw new AuthenticationException("User is inactive.");
		}
		if(user.isTemporaryPassword()) {
			throw new AuthenticationException("Please set the permanent password.");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		Hibernate.initialize(user.getRole());
		Role role = user.getRole();
		System.out.println("user: "+user.getRole().toString());
		/*
		 * for (Permission permission : role.getPermissions()) { String
		 * rolePermissionString =
		 * role.getName().concat(".").concat(permission.getName()); authorities.add(new
		 * SimpleGrantedAuthority(rolePermissionString)); }
		 */
		
		for (String permission : role.getPermissions()) {
			String rolePermissionString = role.getName().concat("_").concat(permission);
			System.out.println("rolePermissionString : "+rolePermissionString);
			authorities.add(new SimpleGrantedAuthority(rolePermissionString));
		}
		
		UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(), authorities);
		return userDetails;
	}

}
