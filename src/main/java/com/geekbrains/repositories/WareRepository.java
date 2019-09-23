package com.geekbrains.repositories;

import com.geekbrains.entities.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WareRepository extends PagingAndSortingRepository<Product, Long>, JpaSpecificationExecutor<Product> {
}