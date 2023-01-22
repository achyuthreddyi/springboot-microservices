package com.achyuthreddyi.orderservice.repository;

import com.achyuthreddyi.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
