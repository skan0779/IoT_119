package com.example.demo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultDTO {
	
    private Integer customerId;
    
    private String customer_category;
    
    private LocalDate usage_date;
    
    private Integer usage_per_day;
    
}