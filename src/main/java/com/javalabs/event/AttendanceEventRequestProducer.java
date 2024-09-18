package com.javalabs.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.javalabs.dto.AttendanceEventRequest;

@Component
public class AttendanceEventRequestProducer {

    private static final String REQUEST_TOPIC = "attendance-request";
	@Autowired
    private KafkaTemplate<String, AttendanceEventRequest> kafkaTemplate;

    public void sendLeaveRequest(AttendanceEventRequest request) {
        kafkaTemplate.send(REQUEST_TOPIC, request.getManagerId().toString(), request);
    }
}
