package com.ambh.quartz.quartzspringtest.services;

import org.springframework.stereotype.Service;

@Service
public class SimpleService {

    public void writeSomething() {
        System.out.println("This is a Quartz job in Spring!");
    }
}
