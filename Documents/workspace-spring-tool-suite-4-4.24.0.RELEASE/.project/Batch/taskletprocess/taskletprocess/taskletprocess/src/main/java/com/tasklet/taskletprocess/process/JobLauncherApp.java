package com.tasklet.taskletprocess.process;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

public class JobLauncherApp {

    private final JobLauncher jobLauncher;
    private final Job taskletJob;

    public JobLauncherApp(JobLauncher jobLauncher, Job taskletJob) {
        this.jobLauncher = jobLauncher;
        this.taskletJob = taskletJob;
    }

    public void scheduleTasklet() {
        try {
            // Pass unique parameters using timestamp
            JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()) // Ensures uniqueness
                .toJobParameters();

            jobLauncher.run(taskletJob, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
