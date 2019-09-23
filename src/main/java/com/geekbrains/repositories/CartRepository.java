package com.geekbrains.repositories;

import com.geekbrains.entities.Cart;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartRepository extends PagingAndSortingRepository<Cart, Long>, JpaSpecificationExecutor<Cart> {
    @Query("select c from Cart c where c.productId = :productId and c.clientId = :clientId")
    Cart getProductByUser(@Param("productId") Long productId, @Param("clientId") Long clientId);

    @Modifying
    @Transactional
    @Query("delete from Cart c where c.productId = :productId and c.clientId = :clientId")
    void delProductByUser(@Param("productId") Long productId, @Param("clientId") Long clientId);
}