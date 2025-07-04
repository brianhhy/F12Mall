package com.avgmax.trade.domain;

import java.math.BigDecimal;
import java.util.UUID;

import com.avgmax.global.base.BaseTimeEntity;
import com.avgmax.trade.domain.enums.OrderType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCoin extends BaseTimeEntity {
    
    @Builder.Default
    private String userCoinId = UUID.randomUUID().toString();

    private String holderId;

    private String coinId;

    @Builder.Default
    private BigDecimal holdQuantity = new BigDecimal(1000);

    @Builder.Default
    private BigDecimal totalBuyAmount = new BigDecimal(1000000);

    public void processCoin(OrderType orderType, BigDecimal quantity, BigDecimal unitPrice) {
        BigDecimal amount = unitPrice.multiply(quantity);
        this.holdQuantity = orderType == OrderType.BUY ? 
            this.holdQuantity.add(quantity) : 
            this.holdQuantity.subtract(quantity);
        this.totalBuyAmount = orderType == OrderType.BUY ? 
            this.totalBuyAmount.add(amount) : 
            this.totalBuyAmount.subtract(amount);
    }

    public static UserCoin of(String holderId, String coinId, BigDecimal quantity, BigDecimal unitPrice) {
        return UserCoin.builder()
            .holderId(holderId)
            .coinId(coinId)
            .holdQuantity(quantity)
            .totalBuyAmount(unitPrice.multiply(quantity))
            .build();
    }
}