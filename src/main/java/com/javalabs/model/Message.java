package com.javalabs.model;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Component
@Data
public class Message {

	private String to;
	private String cc;
	private String bcc;
	private String subject;
	private String text;
	private int priority;
	private MultipartFile[] attachments;
}