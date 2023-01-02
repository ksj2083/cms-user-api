package com.cms.order.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.order.domain.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"productItems"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Product> findBySellerIdAndId(Long sellerId, Long id);

    @EntityGraph(attributePaths = {"productItems"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Product> findWithProductItemsById(Long id);
}
