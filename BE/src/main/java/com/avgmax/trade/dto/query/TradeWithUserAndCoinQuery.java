package com.avgmax.trade.dto.query;

import com.avgmax.trade.domain.enums.OrderType;
import com.avgmax.trade.domain.enums.TradeStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TradeWithUserAndCoinQuery {
    // Trade 정보
    private String tradeId;
    private OrderType orderType;
    private Integer quantity;
    private Double unitPrice;
    private LocalDateTime executedAt;
    private TradeStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // User 정보
    private String userId;
    private String userName;
    private String userEmail;
    private String userUsername;
    private String userPwd;
    private String userImageUrl;
    private Double userMoney;

    // Coin 정보
    private String coinId;
    private String coinCreatorId;
}
