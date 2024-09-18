package com.javalabs.dto;

import java.time.LocalDate;

import com.javalabs.model.ApprovalStatus;

import lombok.Data;

@Data
public class AttendanceEventRequest {
    private Long userId;
    private Long managerId;
    private LocalDate date;
    private String leaveType;
	private ApprovalStatus status;
}