package com.example.demo.service;

import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.ResultDTO;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Usage;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.UsageRepository;

@Service
public class CustomerService {
	
	 @Autowired
	 private CustomerRepository customerRepository;
	 
	 @Autowired
	 private UsageRepository usageRepository;
	 
	 @Transactional
	 public void saveCustomerAndUsage(CustomerDTO customerDTO) throws Exception {
	 Customer c = new Customer();
	 c.setCustomer_name(customerDTO.getCustomer_name());
	 c.setCustomer_category(customerDTO.getCustomer_category());
	 Customer c2 = customerRepository.save(c);
	 
	// 1. 웹사이트에서 받은 Form 데이터를 DB에 저장하는 서비스
     MultipartFile file = customerDTO.getCustomer_data();
     try (InputStream inputStream = file.getInputStream()) {
         Workbook workbook = WorkbookFactory.create(inputStream);
         Sheet sheet = workbook.getSheetAt(0);
         Iterator<Row> rows = sheet.iterator();
         boolean isFirstRow = true;

         while (rows.hasNext()) {
             Row row = rows.next();
             
             if (isFirstRow) {
                 isFirstRow = false;
                 continue;
             }
             
             // 날짜 변환
             Date date = row.getCell(0).getDateCellValue();
             LocalDate date2 = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
             
             // 데이터 변환
             Double usage = row.getCell(1).getNumericCellValue();
             Integer usage2 = (int)Math.round(usage);
             
             Usage usageData = new Usage();
             usageData.setUsage_date(date2);
             usageData.setUsage_per_day(usage2);
             usageData.setCustomer(c2);
             usageRepository.save(usageData);
         }
     }
	 }
	 // 2. DB에 저장한 고객 데이터를 조회후 웹사이트로 전송하는 서비스
	 public List<ResultDTO> getResultData() {
		 
		 List<Object[]> results = usageRepository.findResultData();
		 
		 return results.stream().map(result -> new ResultDTO(
	                ((Number) result[0]).intValue(),  // customer_id
	                (String) result[1],               // customer_category
	                ((java.sql.Date) result[2]).toLocalDate(),  // usage_date
	                ((Number) result[3]).intValue()   // usage_per_day
	        )).collect(Collectors.toList());
	 }
}