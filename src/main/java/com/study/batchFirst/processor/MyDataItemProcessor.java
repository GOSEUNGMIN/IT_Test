package com.study.batchFirst.processor;

import com.study.batchFirst.entity.MyData;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyDataItemProcessor implements ItemProcessor<MyData, MyData> {

    @Override
    public MyData process(MyData item) {
        System.out.println("processing :" + item);
        // 읽어온 MyData의 name을 대문자로 변환
        item.setName(item.getName().toUpperCase());
        return item;
    }
}