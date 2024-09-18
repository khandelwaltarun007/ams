package com.javalabs.dto;

import lombok.Data;

@Data
public class CreateUserResponse {
	private Long id;
	private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String contact;
}
