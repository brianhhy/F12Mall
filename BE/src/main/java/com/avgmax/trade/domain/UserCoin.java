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
public class UserCoin extends BaseTimeEntity {
    @Builder.Default
    private String userCoinId = UUID.randomUUID().toString();

    private String holderId;
    private String coinId;

    @Builder.Default
    private BigDecimal holdQuantity = new BigDecimal(1000);

    @Builder.Default
    private BigDecimal buyPrice = new BigDecimal(1000);
}