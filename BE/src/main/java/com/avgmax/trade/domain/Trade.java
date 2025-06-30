package com.avgmax.trade.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.avgmax.global.base.BaseTimeEntity;
import com.avgmax.global.exception.ErrorCode;
import com.avgmax.trade.domain.enums.OrderType;
import com.avgmax.trade.domain.enums.TradeStatus;
import com.avgmax.trade.exception.TradeException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Trade extends BaseTimeEntity {

    @Builder.Default
    private String tradeId = UUID.randomUUID().toString();

    private String userId;

    private String coinId;

    private OrderType orderType;

    private BigDecimal quantity;

    private BigDecimal unitPrice;

    private LocalDateTime executedAt;

    @Builder.Default
    private TradeStatus status = TradeStatus.PENDING;

    public void executeTrade() {
        if (this.status != TradeStatus.PENDING) {
            throw TradeException.of(ErrorCode.TRADE_ALREADY_COMPLETED);
        }
        this.executedAt = LocalDateTime.now();
        this.status = TradeStatus.COMPLETED;
    }

    public void validateOwnership(String userId, String coinId) {
        if (!this.userId.equals(userId)) {
            throw TradeException.of(ErrorCode.TRADE_USER_MISMATCH);
        }
        if (!this.coinId.equals(coinId)) {
            throw TradeException.of(ErrorCode.TRADE_COIN_MISMATCH);
        }
    }
}
