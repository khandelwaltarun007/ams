package com.javalabs.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CreateOptionalWfoResponse {

	private Long id;
	private LocalDate date;
	private boolean isWfoOptional;
}
