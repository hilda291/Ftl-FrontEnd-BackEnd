package com.tasklet.taskletprocess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude = {BatchAutoConfiguration.class})
@ImportResource("classpath:/batch-task.xml")
public class TaskletProcessApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskletProcessApplication.class, args);
    }
}
