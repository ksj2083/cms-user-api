package com.cms.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cms.user.domain.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
