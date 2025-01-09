package com.example.batchChunk.operation;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component("chunkProcessor")
public class ChunkProcessor implements ItemProcessor<String, String>{

	@Override
	public String process(String item) {
		return item.toUpperCase();
	}
}
