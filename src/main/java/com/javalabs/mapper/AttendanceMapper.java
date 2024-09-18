package com.javalabs.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.javalabs.dto.AttendanceCalculationResponse;
import com.javalabs.dto.CreateAttendanceResponse;
import com.javalabs.dto.GetAttendanceResponse;
import com.javalabs.model.ApprovalStatus;
import com.javalabs.model.Attendance;
import com.javalabs.model.AttendanceCalculation;

@Mapper
public interface AttendanceMapper {

	AttendanceMapper INSTANCE = Mappers.getMapper(AttendanceMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "status", expression = "java(statusToString(attendance.getStatus()))")
    CreateAttendanceResponse mapToResponse(Attendance attendance);
    
    List<CreateAttendanceResponse> mapToResponseList(List<Attendance> attendances);
	
    default String statusToString(ApprovalStatus status) {
        return status.name();
    }
    
    default ApprovalStatus stringToStatus(String status) {
        return ApprovalStatus.valueOf(status);
    }
    
    
    @Mapping(source = "user.id",target = "userId")
    GetAttendanceResponse mapToGetResponse(Attendance attendance);
    
    List<GetAttendanceResponse> mapToGetResponseList(List<Attendance> attendances);
    
    @Mapping(source = "user.id", target = "userId")
    AttendanceCalculationResponse mapToResponse(AttendanceCalculation attendanceCalculation);
    
    List<AttendanceCalculationResponse> mapToResponse(List<AttendanceCalculation> attendanceCalculation);
}
