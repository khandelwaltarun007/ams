package com.javalabs.service;

import java.util.List;

import com.javalabs.dto.AttendanceCalculationResponse;

public interface IAttendanceCalculationService {

	AttendanceCalculationResponse calculateAttendance(Long userId);

	List<AttendanceCalculationResponse> getAllAttendanceCalculationsResponse();

}
