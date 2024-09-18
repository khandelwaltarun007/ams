package com.javalabs.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface MailService {

	public void prepareMail(String to, String cc, String bcc, String subject, String text, int priority, MultipartFile[] attachment);

}
