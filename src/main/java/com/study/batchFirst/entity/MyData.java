package com.study.batchFirst.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // JPA Entity 선언
@Data // Lombok : getter/setter, toString
@NoArgsConstructor // Lombok : 기본 생성자
@AllArgsConstructor // Lombok : 모든 필드를 매개변수로 받는 생성자
@Builder
public class MyData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // id 자동 생성
	private Long id;
	
	// CSV name 컬럼
	private String name;
	
	// CSV age 칼럼
	private int age;
}
