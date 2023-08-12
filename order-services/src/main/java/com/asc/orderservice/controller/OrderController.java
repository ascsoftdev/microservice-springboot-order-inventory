package com.asc.orderservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.asc.orderservice.dto.OrderRequest;
import com.asc.orderservice.dto.OrderResonse;
import com.asc.orderservice.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
	
	private final OrderService orderService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String createOrder(@RequestBody OrderRequest orderRequest) throws Exception{
		log.info("Order Placing");
		orderService.placeOrder(orderRequest);
		log.info("order Placed");
		return "Order Placed Successfully";
	}
	
	@GetMapping
	public List<OrderResonse> getAllOrders() {
		return orderService.getAllOrders();
	}
}
