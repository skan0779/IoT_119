package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Usage;

public interface ResultRepository extends JpaRepository<Usage, Long> {

    @Query(value = "SELECT u.customer_id, c.customer_category, u.usage_date, u.usage_per_day " +
                   "FROM usage AS u " +
                   "INNER JOIN customer AS c ON u.customer_id = c.customer_id " +
                   "WHERE u.customer_id = (" +
                   "    SELECT MAX(customer_id) FROM customer" +
                   ")", nativeQuery = true)
    List<Object[]> findResultData();
    
}