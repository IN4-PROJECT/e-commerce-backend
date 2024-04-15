package com.youtube.ecommerce.repository;

import com.youtube.ecommerce.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

//    public Optional<OrderDetail> findById(Integer id);
}


