package com.avgmax.trade.mapper;

import java.util.List;
import java.util.Optional;

import com.avgmax.trade.domain.Trade;

public interface TradeMapper {
    void insert(Trade trade);
    Optional<Trade> selectByTradeId(String tradeId);
    Optional<Trade> selectByTradeIdWithUserAndCoin(String tradeId);
    List<Trade> selectByUserId(String userId);
    void delete(String tradeId);
}
