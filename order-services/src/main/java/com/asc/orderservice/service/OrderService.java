package com.asc.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.asc.orderservice.dto.InventoryResponse;
import com.asc.orderservice.dto.OrderLineItemsDto;
import com.asc.orderservice.dto.OrderRequest;
import com.asc.orderservice.dto.OrderResonse;
import com.asc.orderservice.model.Order;
import com.asc.orderservice.model.OrderLineItems;
import com.asc.orderservice.repository.OrderRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;



@Service
@Transactional
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	
	 public void placeOrder(OrderRequest orderRequest) throws Exception{
		
		List<OrderLineItems> orderLineList = orderRequest.getOrderLineItems()
		.stream().map(orderLineItem -> mapToOrderItemEntity(orderLineItem))
		.toList();
		
		Order order =  new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		order.setOrderLineItems(orderLineList);
		
		List<String> skuCodes = order.getOrderLineItems().stream().map(orderLineItem -> orderLineItem.getSkuCode()).toList();
				
		//Before placing order need to check order present in the inventory or not
		InventoryResponse[] responses = webClientBuilder.build().get()
				.uri("http://inventory-service/api/inventory",
						uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
						.retrieve()
		.bodyToMono(InventoryResponse[].class)
		.block();
		
		Boolean isAvailable = Arrays.stream(responses).allMatch(res -> res.isInStock());
		
		if (isAvailable) {
			orderRepository.save(order);
		} else {
			throw new IllegalArgumentException("Suffiecient Product not Available in Inventory");
		}
		
	}
	 
	public List<OrderResonse> getAllOrders() {
		List<Order> orders = orderRepository.findAllByOrderLineItemsIsNotNull();
		return orders.stream().map(order -> {		
			return OrderResonse.builder().orderNumber(order.getOrderNumber())
			.orderLineItems(order.getOrderLineItems().stream().map(items -> {
				return OrderLineItemsDto.builder()
						.price(items.getPrice())
						.quantity(items.getQuantity())
						.skuCode(items.getSkuCode()).build();
			}).toList()).build();
		}).toList();
	}
	
	public static OrderLineItems mapToOrderItemEntity(OrderLineItemsDto dto) {
		OrderLineItems oItems = new OrderLineItems();
		oItems.setPrice(dto.getPrice());
		oItems.setQuantity(dto.getQuantity());
		oItems.setSkuCode(dto.getSkuCode());
		return oItems;
				
				
	}
}
