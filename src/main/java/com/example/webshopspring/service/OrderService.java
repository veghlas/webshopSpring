package com.example.webshopspring.service;

import com.example.webshopspring.domain.Order;
import com.example.webshopspring.domain.OrderInfo;
import com.example.webshopspring.domain.Product;
import com.example.webshopspring.dto.OrderCreateCommand;
import com.example.webshopspring.dto.OrderUpdateCommand;
import com.example.webshopspring.dto.ProductInfo;
import com.example.webshopspring.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private OrderRepository orderRepository;
    private ModelMapper modelMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    public OrderInfo saveOrder(OrderCreateCommand command) {
        Order order = modelMapper.map(command, Order.class);
        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderInfo.class);
    }

    public List<OrderInfo> listOrders() {
        List<Order> orderList = orderRepository.findAll();
        List<OrderInfo> orderInfoList = new ArrayList<>();
        orderList.forEach(order -> {
            // az adott order orderInfo-vá alakítása
            OrderInfo orderInfo = modelMapper.map(order, OrderInfo.class);

            // az adott order product-jait productInfo-vá alakítjuk. Ez egy lista (productInfoList) mivel az adott order több terméket tartalmazhat.
            List<ProductInfo> productInfoList = order.getProductList().stream()
                    .map(product -> modelMapper.map(product, ProductInfo.class))
                    .collect(Collectors.toList());
            // Itt már kész az adott orderhez tartozó productInfoList (allista)
            //Így az adott orderre be lehet settelni a productInfoListet. (Ha megnézzük, akkor látszik, hogy az OrderInfo.class-ban muszály ProductInfo-kat tartalmaznia a productList-nek.
            orderInfo.setProductList(productInfoList);

            //az adott order hozzáadása a fent létrehozott üres orderInfoList-hez. Itt már minden elemhez besettelve a productInfoList
            orderInfoList.add(orderInfo);
        });
        return orderInfoList;
    }

    public Order findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new RuntimeException();
        }
        return order.get();
    }

    public OrderInfo getOrderById(Long id) {
        return modelMapper.map(findById(id), OrderInfo.class);
    }

    public OrderInfo updateOrderById(Long id, OrderUpdateCommand command) {
        Order order = findById(id);
        modelMapper.map(command, order);
        return modelMapper.map(order, OrderInfo.class);
    }

    public void delete(Long id) {
        Order order = findById(id);
        orderRepository.delete(order);
    }
}
