package com.wangzi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages={"com.wangzi.**.**"})
@SpringBootApplication
public class ApplicationStart {
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(ApplicationStart.class, args);
		String[] beanNames = ctx.getBeanNamesForAnnotation(Repository.class);
		for(String bn:beanNames){
			System.out.println(bn);
		}
	}
}
