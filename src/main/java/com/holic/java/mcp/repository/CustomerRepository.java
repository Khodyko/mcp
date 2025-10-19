package com.holic.java.mcp.repository;

import com.holic.java.mcp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
