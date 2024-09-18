package com.javalabs.dto;

import lombok.Data;

@Data
public class AttendanceEventResponse {
    private Long userId;
    private String status;
    private String comments;

}