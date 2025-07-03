package com.avgmax.trade.dto.response;

import com.avgmax.trade.domain.ClosingPrice;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.List;

@Getter
@Builder
public class ChartResponse {
    private String chartDate;
    private BigDecimal valClose;
    private BigDecimal valHigh;
    private BigDecimal valLow;

    public static List<ChartResponse> from(List<ClosingPrice> closingPrice) {
        return closingPrice.stream()
            .map(cp -> ChartResponse.builder()
                .chartDate(cp.getTradeDate().toString())
                .valClose(cp.getUnitPrice())
                .valHigh(cp.getHighPrice())
                .valLow(cp.getLowPrice())
                .build())
            .collect(Collectors.toList());
    }
}
