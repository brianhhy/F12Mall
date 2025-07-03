package com.avgmax.trade.dto.response;

import com.avgmax.trade.dto.query.CoinWithCreatorWithProfileQuery;
import com.avgmax.trade.dto.query.TradeGroupByCoinQuery;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TradeFetchResponse {

    private String coinName; //user
    private String userName; //user
    private BigDecimal currentPrice; //coin
    private BigDecimal closingPrice; //coin
    private BigDecimal changePrice; //coin
    private BigDecimal fluctuationRate; //coin
    private BigDecimal tradeVolume; //trade
    private BigDecimal lowestPrice; //trade
    private BigDecimal highestPrice; //trade
    private String profileImage; //user
    private String position; //profile
    private String bio; //profile

    public static TradeFetchResponse from(CoinWithCreatorWithProfileQuery coin, TradeGroupByCoinQuery tradeByCoin) {
        return TradeFetchResponse.builder()
                .coinName(coin.getCreatorUsername())
                .userName(coin.getCreatorName())
                .currentPrice(coin.getCurrentPrice())
                .changePrice(coin.getChangePrice())
                .fluctuationRate(coin.getFluctuationRate())
                .tradeVolume(tradeByCoin.getTradeVolume())
                .lowestPrice(tradeByCoin.getLowestPrice())
                .highestPrice(tradeByCoin.getHighestPrice())
                .profileImage(coin.getCreatorImage())
                .position(coin.getProfilePosition())
                .bio(coin.getProfileBio())
                .build();
    }
}
