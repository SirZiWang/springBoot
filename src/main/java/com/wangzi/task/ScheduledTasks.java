package com.wangzi.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
 
	@Scheduled(cron = "0 */3 *  * * * ")
	public void scheduledTask(){
		System.out.println("定时任务");
	}
}
