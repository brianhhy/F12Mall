package com.avgmax.trade.mapper;

import java.util.List;

import com.avgmax.trade.domain.Trade;

public interface TradeMapper {
    void insert(Trade trade);
    Trade selectById(String tradeId);
    Trade selectByIdWithUserAndCoin(String tradeId);
    List<Trade> selectByUserId(String userId);
}
