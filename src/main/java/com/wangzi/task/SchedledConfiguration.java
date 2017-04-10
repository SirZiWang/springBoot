package com.wangzi.task;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class SchedledConfiguration {
	@Value("${job.query.timer}")
	private String queryTimer;
	
	@Value("${job.persistent.bill.timer}")
	private String insertBillTimer;
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(
			@Qualifier("queryRedpacketJobTrigger") Trigger queryRedpacketJobTrigger,
			@Qualifier("insertBillJobTrigger") Trigger insertBillJobTrigger) throws IOException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		// this allows to update triggers in DB when updating settings in config
		// file:
		// 用于quartz集群,QuartzScheduler
		// 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
		factory.setOverwriteExistingJobs(true);
		// 用于quartz集群,加载quartz数据源
		// factory.setDataSource(dataSource);
		// QuartzScheduler 延时启动，应用启动完10秒后 QuartzScheduler 再启动
		// factory.setStartupDelay(10);
		// 用于quartz集群,加载quartz数据源配置
		// factory.setQuartzProperties(quartzProperties());
		factory.setAutoStartup(true);
		factory.setApplicationContextSchedulerContextKey("applicationContext");
		// 注册触发器
		factory.setTriggers(queryRedpacketJobTrigger, insertBillJobTrigger);// 直接使用配置文件
		// factory.setConfigLocation(new
		// FileSystemResource(this.getClass().getResource("/quartz.properties").getPath()));
		return factory;
	}

	/**
	 * 24小时后查询是否有未领取的红包job
	 * 
	 * @return
	 */
	@Bean
	public JobDetailFactoryBean queryRedpacketJobDetail() {
		return createJobDetail(InvokingJobDetailFactory.class, "queryRedpacketGroup", "scheduledJob", "queryRedpacket");
	}

	/**
	 * 定时记录流水
	 * 
	 * @return
	 */
	@Bean
	public JobDetailFactoryBean insertBillJobDetail() {
		return createJobDetail(InvokingJobDetailFactory.class, "insertRedpacketBillGroup", "scheduledJob",
				"insertBill");
	}

	/**
	 * 加载触发器
	 * 
	 * @param jobDetail
	 * @return
	 */
	@Bean(name = "queryRedpacketJobTrigger")
	public CronTriggerFactoryBean queryRedpacketJobTrigger(@Qualifier("queryRedpacketJobDetail") JobDetail jobDetail) {
		return createTrigger(jobDetail, queryTimer);
	}

	@Bean(name = "insertBillJobTrigger")
	public CronTriggerFactoryBean insertBillJobTrigger(@Qualifier("insertBillJobDetail") JobDetail jobDetail) {
		return createTrigger(jobDetail, insertBillTimer);
	}

	/**
	 * 创建job工厂
	 * 
	 * @param jobClass
	 * @param groupName
	 * @param targetObject
	 * @return
	 */
	private static JobDetailFactoryBean createJobDetail(Class<?> jobClass, String groupName, String targetObject,
			String targetMethod) {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(jobClass);
		factoryBean.setDurability(true);
		factoryBean.setRequestsRecovery(true);
		factoryBean.setGroup(groupName);
		Map<String, String> map = new HashMap<>();
		map.put("targetObject", targetObject);
		map.put("targetMethod", targetMethod);
		factoryBean.setJobDataAsMap(map);
		return factoryBean;
	}

	/**
	 * 创建触发器工厂
	 * 
	 * @param jobDetail
	 * @param cronExpression
	 * @return
	 */
	private static CronTriggerFactoryBean createTrigger(JobDetail jobDetail, String cronExpression) {
		CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
		factoryBean.setJobDetail(jobDetail);
		factoryBean.setCronExpression(cronExpression);
		return factoryBean;
	}
}
