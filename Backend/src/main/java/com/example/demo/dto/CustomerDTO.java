package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CustomerDTO {
	
    private String customer_name;
    
    private String customer_category;
    
    private MultipartFile customer_data;
    
}