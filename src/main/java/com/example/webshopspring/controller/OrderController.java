package com.example.webshopspring.controller;


import com.example.webshopspring.domain.Order;
import com.example.webshopspring.domain.OrderInfo;
import com.example.webshopspring.dto.OrderCreateCommand;
import com.example.webshopspring.dto.OrderUpdateCommand;
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

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderInfo> listOrderById(@PathVariable("orderId") Long id) {
        log.info("Http request GET / /api/order/{orderId} body: " + id);
        OrderInfo orderInfo = orderService.getOrderById(id);
        return new ResponseEntity<>(orderInfo, HttpStatus.OK);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderInfo> updateById(@PathVariable("orderId") Long id,
                                                @RequestBody @Valid OrderUpdateCommand command) {
        log.info("Http request PUT / /api/order/{orderId} body: " + command.toString() + "with variable" + id);
        OrderInfo orderInfo = orderService.updateOrderById(id, command);
        return new ResponseEntity<>(orderInfo, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteById(@PathVariable("orderId") Long id) {
        log.info("Http request DELETE / /api/order/{orderId} with variable: " + id);
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
