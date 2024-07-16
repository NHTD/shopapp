package com.example.shopapp.repositories;

import com.example.shopapp.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where" +
            "(:keyword is null or :keyword = '' or o.fullName like %:keyword% or o.address like %:keyword% " +
            "or o.email like %:keyword%) ")
    Page<Order> findByKeyword(String keyword, Pageable pageable);
}
