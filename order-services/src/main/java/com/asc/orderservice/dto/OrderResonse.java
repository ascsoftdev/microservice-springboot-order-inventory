package com.asc.orderservice.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class OrderResonse {
	private String orderNumber;
	private List<OrderLineItemsDto> orderLineItems;
	
}
