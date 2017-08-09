package com.wangzi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.wangzi.kafka.consumer.ConsumerDemo;

@EnableDiscoveryClient
@Configuration
@ComponentScan(basePackages={"com.wangzi.**.**"})
@SpringBootApplication
@EnableScheduling
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

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
