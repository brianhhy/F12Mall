package com.avgmax.trade.dto.query;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CoinWithCreatorQuery {

    // Coin 정보
    private String coinId;
    private BigDecimal currentPrice;
    private BigDecimal closingPrice;

    // Creator 정보
    private String creatorUsername;
}
