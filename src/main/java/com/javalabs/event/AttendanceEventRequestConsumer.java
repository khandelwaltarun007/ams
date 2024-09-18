package com.javalabs.event;

import org.springframework.kafka.annotation.KafkaListener;

import com.javalabs.dto.AttendanceEventRequest;

public class AttendanceEventRequestConsumer {

	private static final String TOPIC = "approval-topic";
    private Long managerId;


    @KafkaListener(topics = TOPIC, groupId = "approval-group")
    public void consumeMessage(AttendanceEventRequest message) {
        if (message.getManagerId() == managerId) {
            System.out.println("Received message for managerId " + managerId + ": " + message.getUserId());
        }
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }
	
}
