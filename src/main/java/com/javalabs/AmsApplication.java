package com.javalabs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//@EnableDiscoveryClient
public class AmsApplication{

	public static void main(String[] args) {
		SpringApplication.run(AmsApplication.class, args);
	}
	

}
