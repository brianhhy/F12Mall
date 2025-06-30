package com.avgmax.trade.dto.response;

import java.math.BigDecimal;

import com.avgmax.trade.domain.Trade;
import com.avgmax.trade.domain.enums.OrderType;
import com.avgmax.trade.domain.enums.TradeStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TradeUserCoinResponse {
    private String coinId;
    private OrderType orderType;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private TradeStatus status;

    public static TradeUserCoinResponse from(Trade trade){
        return TradeUserCoinResponse.builder()
            .coinId(trade.getCoinId())
            .orderType(trade.getOrderType())
            .quantity(trade.getQuantity())
            .unitPrice(trade.getUnitPrice())
            .build();
    }
}
