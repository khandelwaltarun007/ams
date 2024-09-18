package com.javalabs.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javalabs.dto.CreateAttendanceRequest;
import com.javalabs.exception.common.EntityNotFoundException;
import com.javalabs.exception.common.MissingMandatoryFieldException;
import com.javalabs.model.ApprovalStatus;
import com.javalabs.model.Attendance;
import com.javalabs.model.User;
import com.javalabs.repository.IAttendanceRepository;
import com.javalabs.service.IAttendanceService;
import com.javalabs.service.IUserService;

@Service
public class AttendanceServiceImpl implements IAttendanceService {

	@Autowired
	private IAttendanceRepository attendanceRepository;
	
	@Autowired
	private IUserService userService;
	
	@Override
	public Attendance markAttendance(CreateAttendanceRequest attendanceRequest) {
		User user = userService.findUserByUserId(attendanceRequest.getUserId());
		Attendance attendance = Attendance.builder()
				.user(user)
				.date(attendanceRequest.getDate())
				.type(attendanceRequest.getType())
				.status(ApprovalStatus.PENDING_FOR_APPROVAL)
				.managerUsername(user.getManager().getUsername()).build();
		return attendanceRepository.save(attendance);	
	}

	@Override
	@Transactional(readOnly = true)
	public List<Attendance> getAttendanceByDate(LocalDate date) {
		return attendanceRepository.findByDate(date);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Attendance> getAttendanceByUser(Long userId) {
		User user = userService.findUserByUserId(userId);
		return attendanceRepository.findByUser(user);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Attendance> getAttendanceByUser(User user) {
		return attendanceRepository.findByUser(user);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Attendance> getApprovedAttendanceByUser(User user) {
		return attendanceRepository.findByUserAndStatus(user, ApprovalStatus.APPROVED);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Attendance> getRejectedAttendanceByUser(User user) {
		return attendanceRepository.findByUserAndStatus(user, ApprovalStatus.REJECTED);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Attendance> getApprovedAttendanceByManagerUsername(String username) {
		return attendanceRepository.findByManagerUsernameAndStatus(username, ApprovalStatus.APPROVED);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Attendance> getRejectedAttendanceByManagerUsername(String username) {
		return attendanceRepository.findByManagerUsernameAndStatus(username, ApprovalStatus.REJECTED);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Attendance> getPendingForApprovalAttendanceByUser(User user) {
		return attendanceRepository.findByUserAndStatus(user, ApprovalStatus.PENDING_FOR_APPROVAL);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Attendance> getPendingForApprovalAttendanceByManagerUsername(String username) {
		return attendanceRepository.findByManagerUsernameAndStatus(username, ApprovalStatus.PENDING_FOR_APPROVAL);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Attendance> getAllAttendance() {
		return attendanceRepository.findAll();
	}
	
	@Override
	public void changeStatusForAttendance(User user, LocalDate date, ApprovalStatus status, String comments) {
		Attendance attendance = attendanceRepository.findByUserAndDate(user, date).orElseThrow(() -> new EntityNotFoundException("Attendance not logged for date: "+date));
		attendance.setStatus(status);
		attendanceRepository.save(attendance);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Attendance> getAttendanceByStatus(ApprovalStatus status, String username, Long userId) {
		if(Strings.isBlank(username) && userId == 0L) {
			throw new MissingMandatoryFieldException("Either manager username or userId is required.");
		}
		switch(status) {
		case PENDING_FOR_APPROVAL:
			if(Strings.isNotBlank(username))
				return getPendingForApprovalAttendanceByManagerUsername(username);
			else {
				User user = userService.findUserByUserId(userId);
				return getPendingForApprovalAttendanceByUser(user);
			}
		case APPROVED:
			if(Strings.isNotBlank(username))
				return getApprovedAttendanceByManagerUsername(username);
			else {
				User user = userService.findUserByUserId(userId);
				return getApprovedAttendanceByUser(user);
			}
		case REJECTED:
			if(Strings.isNotBlank(username))
				return getRejectedAttendanceByManagerUsername(username);
			else {
				User user = userService.findUserByUserId(userId);
				return getRejectedAttendanceByUser(user);
			}
		default:
			throw new MissingMandatoryFieldException("Status is invalid.");
		}
	}
}