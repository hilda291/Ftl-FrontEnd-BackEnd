package com.example.batchProj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude = {BatchAutoConfiguration.class})
@ImportResource("classpath:/Batch-tasklet.xml")
public class BatchProjApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchProjApplication.class, args);
	}

}
