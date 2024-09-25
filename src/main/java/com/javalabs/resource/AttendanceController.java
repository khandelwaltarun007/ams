package com.javalabs.resource;

import java.time.LocalDate;
import java.util.Collections;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javalabs.dto.CreateAttendanceRequest;
import com.javalabs.dto.CreateAttendanceResponse;
import com.javalabs.dto.GetAttendanceResponse;
import com.javalabs.exception.common.MissingMandatoryFieldException;
import com.javalabs.mapper.AttendanceMapper;
import com.javalabs.model.ApprovalStatus;
import com.javalabs.model.Attendance;
import com.javalabs.model.User;
import com.javalabs.service.IUserService;
import com.javalabs.service.impl.AttendanceServiceImpl;

@RestController
public class AttendanceController {

	@Autowired
    private AttendanceServiceImpl attendanceService;
	
	@Autowired
	private IUserService userService;

    @PostMapping("/attendance")
    public ResponseEntity<CreateAttendanceResponse> markAttendance(@RequestBody CreateAttendanceRequest createAttendanceRequest) {
    	Attendance attendance = attendanceService.markAttendance(createAttendanceRequest);
    	CreateAttendanceResponse attendanceResponse = new CreateAttendanceResponse();
		BeanUtils.copyProperties(attendance, attendanceResponse);
		attendanceResponse = AttendanceMapper.INSTANCE.mapToResponse(attendance);
		return new ResponseEntity<>(attendanceResponse, HttpStatus.CREATED);
    }

    @GetMapping("/attendance/user/{userId}")
    public ResponseEntity<Page<GetAttendanceResponse>> getAttendanceByEmployee(@PathVariable Long userId,
    		@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        User user = userService.findUserByUserId(userId);
        Pageable pageable = PageRequest.of(page, size);
        Page<Attendance> attendances = attendanceService.getAttendanceByUser(user, pageable);
        Page<GetAttendanceResponse> attendancesResponse = new PageImpl<>(Collections.emptyList(), pageable, 0);
		BeanUtils.copyProperties(attendances, attendancesResponse);
		attendancesResponse = AttendanceMapper.INSTANCE.mapToGetResponseList(attendances);
        return new ResponseEntity<>(attendancesResponse, HttpStatus.OK);
    }

    @GetMapping("/attendance/date/{date}")
    public ResponseEntity<Page<GetAttendanceResponse>> getAttendanceByDate(@PathVariable String date,
    		@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        LocalDate parsedDate = LocalDate.parse(date);
        Pageable pageable = PageRequest.of(page, size);
        Page<Attendance> attendances = attendanceService.getAttendanceByDate(parsedDate, pageable);
        Page<GetAttendanceResponse> attendancesResponse = new PageImpl<>(Collections.emptyList(), pageable, 0);
		BeanUtils.copyProperties(attendances, attendancesResponse);
		attendancesResponse = AttendanceMapper.INSTANCE.mapToGetResponseList(attendances);
        return new ResponseEntity<>(attendancesResponse, HttpStatus.OK);
    }

    @GetMapping("/attendance")
    public ResponseEntity<Page<GetAttendanceResponse>> getAllAttendance(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
    	Pageable pageable = PageRequest.of(page, size);
        Page<Attendance> attendances = attendanceService.getAllAttendance(pageable);
        Page<GetAttendanceResponse> attendancesResponse = new PageImpl<>(Collections.emptyList(), pageable, 0);
		BeanUtils.copyProperties(attendances, attendancesResponse);
		attendancesResponse = AttendanceMapper.INSTANCE.mapToGetResponseList(attendances);
        return new ResponseEntity<>(attendancesResponse, HttpStatus.OK);
    }
    
    @PostMapping("/attendance/{userId}/date/{date}/status/{status}/comments/{comments}")
    public ResponseEntity<String> changeStatusForAttendance(@PathVariable("userId") Long userId, @PathVariable("date") LocalDate date, @PathVariable(name = "status", required = true) ApprovalStatus status, @PathVariable(name = "comments",required = false) String comments){
    	if(null==status) {
    		throw new MissingMandatoryFieldException("status is not valid or missing.");
    	}
    	User user = userService.findUserByUserId(userId);
    	attendanceService.changeStatusForAttendance(user, date, status, comments);
    	return new ResponseEntity<>("Status for attendance has been changed successfully.",HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/attendance/filter")
    public ResponseEntity<Page<GetAttendanceResponse>> getAttendanceByStatus(@RequestParam ApprovalStatus status,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String comments,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
    	if(null==status) {
    		throw new MissingMandatoryFieldException("status is not valid or missing.");
    	}
    	Pageable pageable = PageRequest.of(page, size);
    	Page<Attendance> attendances = attendanceService.getAttendanceByStatus(status, username, userId, pageable);
    	Page<GetAttendanceResponse> attendancesResponse = new PageImpl<>(Collections.emptyList(), pageable, 0);
		BeanUtils.copyProperties(attendances, attendancesResponse);
		attendancesResponse = AttendanceMapper.INSTANCE.mapToGetResponseList(attendances);
    	return ResponseEntity.ok(attendancesResponse);
    }
}