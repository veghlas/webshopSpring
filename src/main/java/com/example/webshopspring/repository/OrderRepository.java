package com.example.webshopspring.repository;

import com.example.webshopspring.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
