package com.study.batchFirst.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.study.batchFirst.entity.MyData;
import com.study.batchFirst.processor.MyDataItemProcessor;
import com.study.batchFirst.repository.MyDataRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

	@Autowired private JobRepository jobRepo;
	@Autowired private PlatformTransactionManager txManager;
	@Autowired private MyDataRepository repo;
	
	@Bean
	public FlatFileItemReader<MyData> reader() {
		FlatFileItemReader<MyData> reader = new FlatFileItemReader<>(); // csv 리더 생성
		reader.setResource(new ClassPathResource("data.csv"));          // classpath/data.csv 파일 지정
		reader.setLinesToSkip(1);										// 헤더 첫 번째 줄 스킵, CSV 파일의 첫 줄은 컬럼명이기 때문에
	
		// CSV 한 줄을 MyData 객체로 변환하는 "라인 매퍼"를 설정 해주는 작업.
		DefaultLineMapper<MyData> mapper = new DefaultLineMapper<>();
		mapper.setLineTokenizer(new DelimitedLineTokenizer() {{			// 토크나이즈 설정 : 콤바로 구분
			setNames("name", "age");									// 첫 번째 토큰 "name", 두 번째 토큰 "age"
		}});
		
		// FieldSetMapper 역할 : 필드들을 MyData 객체로 변환
		mapper.setFieldSetMapper(fs ->
				// FieldSet에서 꺼내서 MyData 객체로 변환
				new MyData(
						null,				   // id : 배치 초기에는 null
						fs.readString("name"), // name 토큰 가져오기
						fs.readInt("age")      // age 토큰을 정수로 가져오기
				)
		);
		
		reader.setLineMapper(mapper);
		
		return reader;
	}
	
	@Bean
	public MyDataItemProcessor processor() {
	   return new MyDataItemProcessor();
	}
	   
	@Bean
	public ItemWriter<MyData> writer() {
	   //람다식으로 ItemWriter 구현
	   return items -> repo.saveAll(items);
	}
	   
	@Bean
	public Step step1() {
	   return new StepBuilder("step1", jobRepo)
	         .<MyData, MyData>chunk(10, txManager)
	         .reader(reader())
	         .processor(processor())
	         .writer(writer())
	         .build();
	}
	   
	   
	@Bean(name = "importJob")
	public Job importJob() {
	   //JobBuilber로 job을 생성
	   return new JobBuilder("importJob",jobRepo)
			.start(step1())                  //step1 부터 시작
            .build();
	}
}
