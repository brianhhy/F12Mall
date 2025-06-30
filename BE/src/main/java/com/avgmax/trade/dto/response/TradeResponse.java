package com.avgmax.trade.dto.response;

import com.avgmax.trade.domain.Trade;
import com.avgmax.trade.domain.enums.OrderType;
import com.avgmax.trade.domain.enums.TradeStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TradeResponse {
    private String tradeId;
    private String userId;
    private String coinId;
    private OrderType orderType;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private LocalDateTime executedAt;
    private TradeStatus status;

    public static TradeResponse from(Trade trade) {
        return TradeResponse.builder()
            .tradeId(trade.getTradeId())
            .userId(trade.getUserId())
            .coinId(trade.getCoinId())
            .orderType(trade.getOrderType())
            .quantity(trade.getQuantity())
            .unitPrice(trade.getUnitPrice())
            .executedAt(trade.getExecutedAt())
            .status(trade.getStatus())
            .build();
    }
}