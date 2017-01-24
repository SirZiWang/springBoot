package com.wangzi.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ApplicationTest {
	
	@RequestMapping(value="/")
	String home(){
		return "Hello Word";
	}
	public static void main(String[] args) {
		SpringApplication.run(ApplicationTest.class, args);
	}
}
