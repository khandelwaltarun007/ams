package com.javalabs.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CreateAttendanceResponse {
    private Long id;
    
    private Long userId;
    private LocalDate date;
    private String type;
    private String status;
    private String comments;
}
