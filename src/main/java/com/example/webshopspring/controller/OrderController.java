package com.example.webshopspring.controller;


import com.example.webshopspring.domain.Order;
import com.example.webshopspring.domain.OrderInfo;
import com.example.webshopspring.dto.OrderCreateCommand;
import com.example.webshopspring.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderInfo> saveOrder(@Valid @RequestBody OrderCreateCommand command) {
        log.info("Http request POST / /api/order body: " + command.toString());
        OrderInfo orderInfo = this.orderService.saveOrder(command);
        return new ResponseEntity<>(orderInfo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderInfo>> listAllOrders() {
        log.info("Http request GET / /api/order");
        List<OrderInfo> orderInfoList = orderService.listOrders();
        return new ResponseEntity<>(orderInfoList, HttpStatus.OK);
    }
}
