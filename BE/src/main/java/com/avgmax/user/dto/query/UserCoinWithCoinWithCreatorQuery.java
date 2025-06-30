package com.avgmax.user.dto.query;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCoinWithCoinWithCreatorQuery {
    //UserCoin
    private String userCoinId;
    private String holderId;
    private BigDecimal holdQuantity;
    private BigDecimal totalBuyAmount;
    //Coin
    private String coinId;
    private String creatorId;
    //Creator
    private String creatorName;
    private String creatorImage;
    private String creatorUsername;
}
