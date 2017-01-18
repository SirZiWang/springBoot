package com.wangzi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages={"com.wangzi.**.**"})
@SpringBootApplication
public class ApplicationStart {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationStart.class, args);
	}
}
