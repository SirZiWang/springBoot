package com.wangzi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import com.wangzi.kafka.consumer.ConsumerDemo;

@EnableAutoConfiguration
@Configuration
@MapperScan("com.wangzi.dao")
@ComponentScan(basePackages={"com.wangzi.**.**"})
@SpringBootApplication
public class ApplicationStart {
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(ApplicationStart.class, args);
		String filePath = "/consumer.properties";
		try {
			ConsumerDemo consumer = new ConsumerDemo(filePath, 1, 100);
			Thread thread = new Thread(consumer);
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String[] beanNames = ctx.getBeanNamesForAnnotation(Repository.class);
		for(String bn:beanNames){
			System.out.println(bn);
		}
	}
}
