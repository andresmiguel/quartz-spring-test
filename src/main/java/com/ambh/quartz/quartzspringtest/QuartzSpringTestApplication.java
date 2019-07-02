package com.ambh.quartz.quartzspringtest;

import com.ambh.quartz.quartzspringtest.jobs.AutoWiringSpringBeanJobFactory;
import com.ambh.quartz.quartzspringtest.jobs.SimpleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.io.IOException;

@SpringBootApplication
public class QuartzSpringTestApplication {

	@Autowired
	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(QuartzSpringTestApplication.class, args);
	}

	@Bean
	public JobDetail jobDetail() {
		return JobBuilder.newJob().ofType(SimpleJob.class)
				.storeDurably()
				.withIdentity("Q_Job_Detail_1")
				.build();
	}

	@Bean
	public Trigger trigger(JobDetail jobDetail) {
		return TriggerBuilder.newTrigger().forJob(jobDetail)
				.withIdentity("Q_Trigger_1")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInSeconds(10))
				.build();
	}

	@Bean
	public SpringBeanJobFactory springBeanJobFactory() {
		AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	@Bean
	public Scheduler scheduler(Trigger trigger, JobDetail jobDetail) throws SchedulerException, IOException {
		StdSchedulerFactory factory = new StdSchedulerFactory();
		factory.initialize(new ClassPathResource("quartz.properties").getInputStream());

		Scheduler scheduler = factory.getScheduler();
		scheduler.setJobFactory(springBeanJobFactory());
		scheduler.scheduleJob(jobDetail, trigger);

		scheduler.start();

		return scheduler;
	}

}
