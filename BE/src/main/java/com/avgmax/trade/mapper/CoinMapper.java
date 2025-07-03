package com.avgmax.trade.mapper;

import com.avgmax.trade.domain.Coin;
import com.avgmax.trade.dto.query.CoinWithCreatorQuery;

import java.util.List;

public interface CoinMapper {
    public int insert(Coin coin);
    public Coin selectById(String coinId);
    List<CoinWithCreatorQuery> selectAllWithCreator();
}
