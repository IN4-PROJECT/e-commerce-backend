package com.youtube.ecommerce.repository;

import com.youtube.ecommerce.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

//    public Optional<OrderDetail> findById(Integer id);
    public Optional<OrderDetail> findByTransactionId(String transactionId);
}


