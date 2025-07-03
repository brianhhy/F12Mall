package com.avgmax.trade.dto.response;

import com.avgmax.trade.dto.query.CoinWithCreatorQuery;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Builder
public class TradeSurgingResponse {

    private String coinName;
    private BigDecimal fluctuationRate;

    public static TradeSurgingResponse from(CoinWithCreatorQuery surging) {
        return TradeSurgingResponse.builder()
                .coinName(surging.getCreatorUserName())
                .fluctuationRate(
                        surging.getCurrentPrice()
                               .subtract(surging.getClosingPrice())
                               .divide(surging.getClosingPrice(),2, RoundingMode.HALF_UP)
                               .multiply(BigDecimal.valueOf(100)))
                .build();
    }
}
