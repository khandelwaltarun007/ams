package com.javalabs.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.javalabs.dto.CreateUserRequest;
import com.javalabs.dto.CreateUserResponse;
import com.javalabs.dto.GetUserResponse;
import com.javalabs.exception.common.EntityNotFoundException;
import com.javalabs.mapper.UserMapper;
import com.javalabs.model.DayOfWeek;
import com.javalabs.model.PasswordHistory;
import com.javalabs.model.Project;
import com.javalabs.model.Role;
import com.javalabs.model.User;
import com.javalabs.model.WfoDay;
import com.javalabs.repository.IPasswordHistoryRepository;
import com.javalabs.repository.IProjectRepository;
import com.javalabs.repository.IRoleRepository;
import com.javalabs.repository.IUserRepository;
import com.javalabs.service.IUserService;
import com.javalabs.util.CodeUtility;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IPasswordHistoryRepository passwordHistoryRepository;
	
	@Autowired
	private IRoleRepository roleRepository;
	
	@Autowired
	private IProjectRepository projectRepository;
	
	@EventListener(ApplicationReadyEvent.class)
	public void postContructMethod() {
		User user = new User();
		user.setFirstName("admin");
		user.setLastName("admin");
		user.setEmail("admin@admin.com");
		user.setStatus(true);
		user.setPassword(CodeUtility.encodePassword("admin"));
		user.setUsername("admin");
		Role role = new Role();
		role.setName("ADMIN");
		Role createdRole = roleRepository.save(role);
		user.setRole(createdRole);
		userRepository.save(user);
	}

	@Transactional
	@Override
	public CreateUserResponse createUser(CreateUserRequest createRequest) {
		User user = new User();
		BeanUtils.copyProperties(createRequest, user);
		User manager = userRepository.findByUsername(createRequest.getManagerUsername()).orElse(null);
		user.setManager(manager);
		Role role = roleRepository.findByName(createRequest.getRoleName()).orElse(null);
		user.setRole(role);
		Set<Project> projects = projectRepository.findByNameIn(createRequest.getProject());
		user.setProjects(projects.isEmpty() ? null : projects);
		user.setPassword(CodeUtility.encodePassword(createRequest.getPassword()));
		user.setTemporaryPassword(true);
		user.setStatus(true);
		user = userRepository.save(user);
		CreateUserResponse createUserResponse = new CreateUserResponse();
		BeanUtils.copyProperties(user, createUserResponse);
		return createUserResponse;
	}

	@Override
	public GetUserResponse findByUserId(Long userId) {
		GetUserResponse userResponse = new GetUserResponse();
		User user = userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("User does not exist with id: " + userId));
		userResponse = UserMapper.INSTANCE.mapToResponse(user);
		BeanUtils.copyProperties(user, userResponse);
		return userResponse;
	}

	@Override
	public List<GetUserResponse> findAll() {
		List<User> user = userRepository.findAll();
		List<GetUserResponse> userResponse = new ArrayList<>();
		userResponse = UserMapper.INSTANCE.mapToResponseList(user);
		BeanUtils.copyProperties(user, userResponse,"password");
		return userResponse;
	}

	@Override
	public void updatePassword(String username, String password) {
		User user = userRepository.findByUsername(username).orElseThrow(()-> new EntityNotFoundException("User does not exist with username: " + username));
		PasswordHistory history = new PasswordHistory();
		//List<PasswordHistory> list = passwordHistoryRepository.findByUsername(username);
		history.setUsername(username);
		history.setPassword(user.getPassword());
		passwordHistoryRepository.save(history);
		user.setPassword(CodeUtility.encodePassword(password));
		user.setTemporaryPassword(false);
		userRepository.save(user);
	}

	@Override
	public void enableUserByUsername(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(()-> new EntityNotFoundException("User does not exist with username: " + username));
		user.setStatus(true);
		userRepository.save(user);
	}

	@Override
	public GetUserResponse findByUsername(String username) {
		GetUserResponse userResponse = new GetUserResponse();
		User user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User does not exist with username: " + username));
		userResponse = UserMapper.INSTANCE.mapToResponse(user);
		BeanUtils.copyProperties(user, userResponse,"password");
		return userResponse;
	}

	@Override
	public void associateManager(Long userId, Long managerId) {
		User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User does not exist with id: " + userId));
        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException("Manager not found with id: " + managerId));
        user.setManager(manager);
        userRepository.save(user);
	}

	@Override
	public User findUserByUserId(Long userId) {
		User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User does not exist with id: " + userId));
		user.getRole();
		user.getProjects();
		return user;
	}

	@Override
	public void associateRole(Long userId, Long roleId) {
		User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User does not exist with id: " + userId));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleId));
        user.setRole(role);
        userRepository.save(user);
	}

}
