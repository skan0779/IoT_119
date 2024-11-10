package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "usage")
@Entity
public class Usage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer usage_id;
	
	@ManyToOne
    @JoinColumn(name = "customer_id")
	private Customer customer;
	
	private LocalDate usage_date;
	
	private Integer usage_per_day;
	
}
