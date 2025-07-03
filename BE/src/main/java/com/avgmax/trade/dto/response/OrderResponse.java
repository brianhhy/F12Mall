package com.avgmax.trade.dto.response;

import com.avgmax.trade.domain.Order;
import com.avgmax.trade.domain.enums.OrderType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderResponse {
    private String orderId;
    private String userId;
    private String coinId;
    private OrderType orderType;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private BigDecimal orderTotal;

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
            .orderId(order.getOrderId())
            .userId(order.getUserId())
            .coinId(order.getCoinId())
            .orderType(order.getOrderType())
            .quantity(order.getQuantity())
            .unitPrice(order.getUnitPrice())
            .orderTotal(order.getOrderTotal())
            .build();
    }
}