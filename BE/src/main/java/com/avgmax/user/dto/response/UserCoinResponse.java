package com.avgmax.user.dto.response;

import java.math.BigDecimal;

import com.avgmax.user.dto.query.UserCoinWithCoinWithCreatorQuery;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCoinResponse {
    private String coinId;
    private String coinName;
    private String creatorName;
    private BigDecimal holdQuantity; //보유수량 = 보유수량 - 매도완료수량
    private BigDecimal currentBuyAmount; //실시간 금액 * 보유 수량
    private BigDecimal sellableQuantity; //매도 가능 수량 = 매도 가능 수량 - 매도중인수량
    private BigDecimal buyPrice; //매수 시점 단가(평균)
    private BigDecimal totalBuyAmount; //총매입금액 = 매도 가능 수량 * 매수 단가
    private BigDecimal currentPrice; //현재 실시간 금액
    private BigDecimal valuaionRate; //평가 손익

    public static UserCoinResponse.UserCoinResponseBuilder from(UserCoinWithCoinWithCreatorQuery userCoin){
        return UserCoinResponse.builder()
            .coinId(userCoin.getCoinId())
            .coinName(userCoin.getCreatorName())
            .creatorName(userCoin.getCreatorUsername());
    }
}
