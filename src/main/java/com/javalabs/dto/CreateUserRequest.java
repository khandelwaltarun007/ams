package com.javalabs.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {
	
	private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String contact;
    private List<String> project;
    private LocalDate dateOfJoining;
    private String managerUsername;
    private String roleName;

}
