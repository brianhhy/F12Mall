package com.avgmax.trade.mapper;

import java.util.List;
import java.util.Optional;

import com.avgmax.trade.domain.Trade;

public interface TradeMapper {
    List<Trade> selectAllByUserId(String userId);
    Optional<Trade> selectByTradeId(String tradeId);
}
