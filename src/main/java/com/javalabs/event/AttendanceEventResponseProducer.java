package com.javalabs.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.javalabs.dto.AttendanceEventResponse;

@Component
public class AttendanceEventResponseProducer {

	public static final String RESPONSE_TOPIC = "attendance-response";

	 @Autowired
	    private KafkaTemplate<String, AttendanceEventResponse> kafkaTemplate;

	    public void sendLeaveResponse(AttendanceEventResponse response) {
	        kafkaTemplate.send(RESPONSE_TOPIC, response.getUserId().toString(), response);
	    }
}
