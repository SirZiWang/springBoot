package com.wangzi.task;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class ScheduledJob {
	private static Log logger = LogFactory.getLog(ScheduledJob.class);
			
	
	public void queryRedpacket(){  
		logger.info("开始执行查询任务");
	}
	public void insertBill(){  
		logger.info("开始执行插入任务");
	}
}
