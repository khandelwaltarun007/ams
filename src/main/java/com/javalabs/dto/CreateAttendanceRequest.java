package com.javalabs.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CreateAttendanceRequest {
	private Long userId;
	private String type;
	private LocalDate date;
}