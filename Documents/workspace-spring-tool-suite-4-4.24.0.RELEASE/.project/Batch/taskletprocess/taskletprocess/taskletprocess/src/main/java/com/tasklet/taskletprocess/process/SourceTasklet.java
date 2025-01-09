package com.tasklet.taskletprocess.process;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;


@Component("sourceTasklet")
public class SourceTasklet implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		for(int i=0; i<5; i++) {
			
			System.out.println("Lets rock the world, the world is ours...");
		}
		
        return RepeatStatus.FINISHED;
	}
}
