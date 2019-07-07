package com.ambh.quartz.quartzspringtest.jobs;

import com.ambh.quartz.quartzspringtest.services.SimpleService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class SimpleJob implements Job {

    @Autowired
    private SimpleService simpleService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            String schedulerInstanceId = jobExecutionContext.getScheduler().getSchedulerInstanceId();
            System.out.println(String.format("SchedulerId: %s. Date: %s", schedulerInstanceId, jobExecutionContext.getFireTime().toString()));
            JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();

            String name = jobDataMap.getString("name");
            System.out.println("Name: " + name);
            simpleService.writeSomething();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
