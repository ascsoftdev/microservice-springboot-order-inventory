package com.asc.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.asc.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
//	@Query("SELECT o FROM Order o JOIN FETCH o.orderLineItems")
//    List<Order> findAllOrdersWithLineItems();
	
//	@EntityGraph(attributePaths = "orderLineItems")
//    List<Order> findAll();
	
	List<Order> findAllByOrderLineItemsIsNotNull();

}
