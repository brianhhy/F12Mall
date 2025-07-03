package com.avgmax.trade.domain;

import com.avgmax.global.base.BaseTimeEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ClosingPrice extends BaseTimeEntity {

    @Builder.Default
    private String closingPriceId = UUID.randomUUID().toString();

    private String coinId;

    private BigDecimal unitPrice;

    private LocalDateTime tradeDate;

    private BigDecimal highPrice;

    private BigDecimal lowPrice;

    private BigDecimal buyQuantity;

    private BigDecimal sellQuantity;
}
