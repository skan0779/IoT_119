package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.ResultDTO;
import com.example.demo.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api")
@Slf4j
@RestController
public class ApiController {
	
	@Autowired
	private CustomerService customerService;
	
	@CrossOrigin
	@PostMapping("/upload")
	public ResponseEntity<List<ResultDTO>> uploadCustomerData(@ModelAttribute CustomerDTO customerDTO) {
        try {
        	// 1. 웹사이트에서 받은 Form 데이터 DB에 저장하기
            customerService.saveCustomerAndUsage(customerDTO);
            // 2. DB에 저장한 고객 데이터 조회 후 전송하기
            List<ResultDTO> resultData = customerService.getResultData();
            return ResponseEntity.ok(resultData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
