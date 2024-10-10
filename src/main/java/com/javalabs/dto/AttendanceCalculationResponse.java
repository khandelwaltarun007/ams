package com.javalabs.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AttendanceCalculationResponse {
	
	private Long userId;
	private Double percentage;
	private Double absenseDays;
	private Double wfoDays;
	private Double presentDays;
	private LocalDate updateDate;

}
