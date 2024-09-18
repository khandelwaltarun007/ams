package com.javalabs.resource;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javalabs.model.Message;
import com.javalabs.service.MailService;

@RestController
public class MailResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailResource.class);
	
	@Autowired
	private MailService mailService;
	
	@PostMapping(value="/service/mail",produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public void sendMail(@RequestPart String mailMessage,@RequestPart("attachments") MultipartFile[] attachments) throws IOException {
		LOGGER.info("entry sendMail");
		ObjectMapper mapper = new ObjectMapper();
		Message message = mapper.readValue(mailMessage, Message.class);
		mailService.prepareMail(message.getTo(), message.getCc(), message.getBcc(), message.getSubject(), message.getText(), message.getPriority(),attachments);
		LOGGER.info("exit sendMail");
	}
	
}