package com.cms.order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.order.domain.model.ProductItem;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
}
