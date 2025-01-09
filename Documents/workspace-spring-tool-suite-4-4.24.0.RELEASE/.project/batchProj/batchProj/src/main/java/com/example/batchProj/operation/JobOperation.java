package com.example.batchProj.operation;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

public class JobOperation {

	private final JobLauncher jobLauncher;
	private final Job taskletJob;
	
	
	public JobOperation(JobLauncher jobLauncher, Job taskletJob) {
		this.jobLauncher = jobLauncher;
		this.taskletJob = taskletJob;
	}
	
	public void scheduleTasklet() {
		try {
			JobParameters jobParameters=new JobParametersBuilder().
					addLong("time",System.currentTimeMillis()).toJobParameters();
			
			jobLauncher.run(taskletJob, jobParameters);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
