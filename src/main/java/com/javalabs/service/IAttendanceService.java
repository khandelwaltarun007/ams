package com.javalabs.service;

import java.time.LocalDate;
import java.util.List;

import com.javalabs.dto.CreateAttendanceRequest;
import com.javalabs.model.ApprovalStatus;
import com.javalabs.model.Attendance;
import com.javalabs.model.User;

public interface IAttendanceService {
	
	public Attendance markAttendance(CreateAttendanceRequest attendanceRequest);
	
	public List<Attendance> getAttendanceByDate(LocalDate date);
	
	public List<Attendance> getAllAttendance();
	
	public List<Attendance> getAttendanceByUser(Long userId);
	
	public List<Attendance> getAttendanceByUser(User user);
	
	public List<Attendance> getApprovedAttendanceByUser(User user);
	
	public List<Attendance> getRejectedAttendanceByUser(User user);
	
	public List<Attendance> getApprovedAttendanceByManagerUsername(String username);
	
	public List<Attendance> getRejectedAttendanceByManagerUsername(String username);
	
	public List<Attendance> getPendingForApprovalAttendanceByUser(User user);
	
	public List<Attendance> getPendingForApprovalAttendanceByManagerUsername(String username);

	public void changeStatusForAttendance(User user, LocalDate date, ApprovalStatus status, String comments);

	public List<Attendance> getAttendanceByStatus(ApprovalStatus status, String username, Long userId);
}
