package com.avgmax.trade.mapper;

import java.util.List;

import com.avgmax.trade.domain.Trade;

public interface TradeMapper {
    public List<Trade> selectByUserId(String userId);
} 
   
