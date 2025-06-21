package com.study.batchFirst.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.batchFirst.entity.MyData;

public interface MyDataRepository extends JpaRepository<MyData, Long> {}
