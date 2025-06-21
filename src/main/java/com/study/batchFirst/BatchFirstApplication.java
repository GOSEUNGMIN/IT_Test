package com.study.batchFirst;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BatchFirstApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchFirstApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner run(JobLauncher launcher, Job importJob) {
		return args -> launcher.run(
				importJob, 
				new JobParametersBuilder()
						.addLong("time", System.currentTimeMillis())
						.toJobParameters()
		);
	}
}
