package com.javalabs.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javalabs.dto.AttendanceCalculationResponse;
import com.javalabs.service.IAttendanceCalculationService;

@RestController
public class AttendanceCalculationController {

	@Autowired
    private IAttendanceCalculationService attendanceCalculationService;

    @PostMapping("/attendance-calculation")
    public ResponseEntity<AttendanceCalculationResponse> calculateAttendance(@RequestBody Long userId) {
        AttendanceCalculationResponse attendanceCalculation = attendanceCalculationService.calculateAttendance(userId);
        return new ResponseEntity<>(attendanceCalculation, HttpStatus.OK);
    }

    @GetMapping("/attendance-calculation")
    public ResponseEntity<List<AttendanceCalculationResponse>> getAllAttendanceCalculations() {
        List<AttendanceCalculationResponse> attendanceCalculations = attendanceCalculationService.getAllAttendanceCalculationsResponse();
        return new ResponseEntity<>(attendanceCalculations, HttpStatus.OK);
    }
}