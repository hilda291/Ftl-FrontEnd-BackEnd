package com.example.ftl_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.logging.Logger;

@SpringBootApplication
public class FtlProjectApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(FtlProjectApplication.class, args);
		Logger logger = Logger.getLogger(FtlProjectApplication.class.getName());

		logger.info("Started for Json to Json Convertion.........");
	}

}
