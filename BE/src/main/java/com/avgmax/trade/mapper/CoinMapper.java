package com.avgmax.trade.mapper;

import com.avgmax.trade.domain.Coin;

public interface CoinMapper {
    public int insert(Coin coin);
    public Coin selectById(String coinId);
}
