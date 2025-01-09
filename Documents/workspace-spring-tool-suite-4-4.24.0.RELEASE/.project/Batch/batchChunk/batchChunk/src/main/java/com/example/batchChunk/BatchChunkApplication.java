package com.example.batchChunk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude= {BatchAutoConfiguration.class})
@ImportResource("classpath:/Batch-chunk.xml")
public class BatchChunkApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchChunkApplication.class, args);
	}

}
