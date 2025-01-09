package com.example.taskManage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class TaskService {

    private final ScheduledExecutorService scheduledExecutorService;

    @Autowired
    public TaskService(ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @PostConstruct
    public void scheduleTasks() {
        // Schedule a task to run every 5 seconds
        scheduledExecutorService.scheduleAtFixedRate(this::performTask, 0, 5, TimeUnit.SECONDS);
    }

    public void performTask() {
        // Your task logic here
        System.out.println("Task executed at: " + System.currentTimeMillis());
    }
}