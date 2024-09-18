package com.javalabs.dto;

import lombok.Data;

@Data
public class CreateRoleResponse {
	private Long id;
	private String name;
	private CreateRoleResponse parentRole;
}