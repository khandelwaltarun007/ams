package com.javalabs.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javalabs.dto.CreateAttendanceRequest;
import com.javalabs.exception.common.EntityNotFoundException;
import com.javalabs.exception.common.MissingMandatoryFieldException;
import com.javalabs.model.ApprovalStatus;
import com.javalabs.model.Attendance;
import com.javalabs.model.Type;
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
				.type(Type.valueOf(attendanceRequest.getType()))
				.status(ApprovalStatus.PENDING_FOR_APPROVAL)
				.managerUsername(user.getManager().getUsername()).build();
		return attendanceRepository.save(attendance);	
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Attendance> getAttendanceByDate(LocalDate date, Pageable pageable) {
		return attendanceRepository.findByDate(date, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Attendance> getAttendanceByUserId(Long userId, Pageable pageable) {
		User user = userService.findUserByUserId(userId);
		return attendanceRepository.findByUser(user, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Attendance> getAttendanceByUser(User user) {
		return attendanceRepository.findByUser(user);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Attendance> getAttendanceByUser(User user, Pageable pageable) {
		return attendanceRepository.findByUser(user, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Attendance> getApprovedAttendanceByUser(User user, Pageable pageable) {
		return attendanceRepository.findByUserAndStatus(user, ApprovalStatus.APPROVED, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Attendance> getRejectedAttendanceByUser(User user, Pageable pageable) {
		return attendanceRepository.findByUserAndStatus(user, ApprovalStatus.REJECTED, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Attendance> getApprovedAttendanceByManagerUsername(String username, Pageable pageable) {
		return attendanceRepository.findByManagerUsernameAndStatus(username, ApprovalStatus.APPROVED, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Attendance> getRejectedAttendanceByManagerUsername(String username, Pageable pageable) {
		return attendanceRepository.findByManagerUsernameAndStatus(username, ApprovalStatus.REJECTED, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Attendance> getPendingForApprovalAttendanceByUser(User user, Pageable pageable) {
		return attendanceRepository.findByUserAndStatus(user, ApprovalStatus.PENDING_FOR_APPROVAL, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Attendance> getPendingForApprovalAttendanceByManagerUsername(String username, Pageable pageable) {
		return attendanceRepository.findByManagerUsernameAndStatus(username, ApprovalStatus.PENDING_FOR_APPROVAL, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Attendance> getAllAttendance(Pageable pageable) {
		return attendanceRepository.findAll(pageable);
	}
	
	@Override
	public void changeStatusForAttendance(User user, LocalDate date, ApprovalStatus status, String comments) {
		Attendance attendance = attendanceRepository.findByUserAndDate(user, date).orElseThrow(() -> new EntityNotFoundException("Attendance not logged for date: "+date));
		attendance.setStatus(status);
		attendanceRepository.save(attendance);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Attendance> getAttendanceByStatus(ApprovalStatus status, String username, Long userId, Pageable pageable) {
		if(Strings.isBlank(username) && userId == 0L) {
			throw new MissingMandatoryFieldException("Either manager username or userId is required.");
		}
		switch(status) {
		case PENDING_FOR_APPROVAL:
			if(Strings.isNotBlank(username))
				return getPendingForApprovalAttendanceByManagerUsername(username, pageable);
			else {
				User user = userService.findUserByUserId(userId);
				return getPendingForApprovalAttendanceByUser(user, pageable);
			}
		case APPROVED:
			if(Strings.isNotBlank(username))
				return getApprovedAttendanceByManagerUsername(username, pageable);
			else {
				User user = userService.findUserByUserId(userId);
				return getApprovedAttendanceByUser(user, pageable);
			}
		case REJECTED:
			if(Strings.isNotBlank(username))
				return getRejectedAttendanceByManagerUsername(username, pageable);
			else {
				User user = userService.findUserByUserId(userId);
				return getRejectedAttendanceByUser(user, pageable);
			}
		default:
			throw new MissingMandatoryFieldException("Status is invalid.");
		}
	}

	@Override
	public List<Attendance> getAttendanceByUserId(Long userId) {
		User user = userService.findUserByUserId(userId);
		return attendanceRepository.findByUser(user);
	}
}