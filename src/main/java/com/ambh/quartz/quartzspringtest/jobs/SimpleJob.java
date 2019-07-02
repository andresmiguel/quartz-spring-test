package com.ambh.quartz.quartzspringtest.jobs;

import com.ambh.quartz.quartzspringtest.services.SimpleService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleJob implements Job {

    @Autowired
    private SimpleService simpleService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        simpleService.writeSomething();
    }
}
