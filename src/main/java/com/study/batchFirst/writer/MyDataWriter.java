package com.study.batchFirst.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.study.batchFirst.entity.MyData;
import com.study.batchFirst.repository.MyDataRepository;

@Component
public class MyDataWriter implements ItemWriter<MyData> {
	
	private final MyDataRepository repo;
	
	public MyDataWriter(MyDataRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public void write(Chunk<? extends MyData> items) {
		// Chunk 에서 실제로 리스트를 꺼내서 저장해주는 작업
		repo.saveAll(items.getItems());
	}

}
