package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Recommandation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer recommandation_id;
	private Integer customer_id;
	private Integer plan_id;
	@CreatedDate
	private LocalDateTime recommandation_date;
	
}
