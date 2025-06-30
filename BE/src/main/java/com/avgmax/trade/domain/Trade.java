package com.avgmax.trade.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.avgmax.global.base.BaseTimeEntity;
import com.avgmax.trade.domain.enums.OrderType;
import com.avgmax.trade.domain.enums.StatusType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Trade extends BaseTimeEntity{
    private String tradeId;
    private String userId;
    private String coinId;
    private OrderType orderType;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private LocalDateTime excutedAt; 
    private StatusType status;
}
