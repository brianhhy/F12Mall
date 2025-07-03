package com.avgmax.trade.domain;

import java.math.BigDecimal;
import java.util.UUID;

import com.avgmax.global.base.BaseTimeEntity;

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
public class Trade extends BaseTimeEntity {

    @Builder.Default
    private String tradeId = UUID.randomUUID().toString();

    private String coinId;

    private String sellId;

    private String buyId;

    private BigDecimal quantity;

    private BigDecimal unitPrice;
}
