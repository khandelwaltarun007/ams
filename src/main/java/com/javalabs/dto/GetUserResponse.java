package com.javalabs.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.Data;

@Data
public class GetUserResponse {
	private Long id;
	private String firstName;
	private String lastName;
    private String username;
	private String email;
	private String contact;
	private boolean isTemporaryPassword;
	private boolean status;
	private LocalDate dateOfJoining;
	private String roleName;
	private Long roleId;
	private Long managerId;
	private String managerUsername;
    private Set<String> assignedProjects;
}
