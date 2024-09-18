package com.javalabs.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CreateOptionalWfoRequest {

	private LocalDate date;
	private boolean isWfoOptional;
}
