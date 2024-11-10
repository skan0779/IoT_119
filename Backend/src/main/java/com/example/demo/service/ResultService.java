package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ResultDTO;
import com.example.demo.repository.ResultRepository;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    public List<ResultDTO> getResultData() {
        List<Object[]> results = resultRepository.findResultData();

        // Object[]를 DTO로 변환
        return results.stream()
                .map(result -> new ResultDTO(
                		((Number) result[0]).intValue(),  // customer_id
                        (String) result[1],               // customer_category
                        (LocalDate) result[2],        	  // usage_date
                        ((Number) result[3]).intValue()   // usage_per_day
                ))
                .collect(Collectors.toList());
    }
}