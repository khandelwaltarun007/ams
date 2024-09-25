package com.javalabs.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.javalabs.dto.CreateAttendanceRequest;
import com.javalabs.model.ApprovalStatus;
import com.javalabs.model.Attendance;
import com.javalabs.model.User;

public interface IAttendanceService {
	
	public Attendance markAttendance(CreateAttendanceRequest attendanceRequest);
	
	public List<Attendance> getAttendanceByUser(User user);
	
	public Page<Attendance> getAttendanceByUser(User user, Pageable pageable);
	
	public Page<Attendance> getAttendanceByUserId(Long userId, Pageable pageable);
	
	public List<Attendance> getAttendanceByUserId(Long userId);
	
	public Page<Attendance> getAttendanceByDate(LocalDate date, Pageable pageable);
	
	public Page<Attendance> getAllAttendance(Pageable pageable);
	
	public Page<Attendance> getApprovedAttendanceByUser(User user, Pageable pageable);
	
	public Page<Attendance> getRejectedAttendanceByUser(User user, Pageable pageable);
	
	public Page<Attendance> getApprovedAttendanceByManagerUsername(String username, Pageable pageable);
	
	public Page<Attendance> getRejectedAttendanceByManagerUsername(String username, Pageable pageable);
	
	public Page<Attendance> getPendingForApprovalAttendanceByUser(User user, Pageable pageable);
	
	public Page<Attendance> getPendingForApprovalAttendanceByManagerUsername(String username, Pageable pageable);

	public void changeStatusForAttendance(User user, LocalDate date, ApprovalStatus status, String comments);

	public Page<Attendance> getAttendanceByStatus(ApprovalStatus status, String username, Long userId, Pageable pageable);

}
