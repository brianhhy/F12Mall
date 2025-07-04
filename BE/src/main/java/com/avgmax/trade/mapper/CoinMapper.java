package com.avgmax.trade.mapper;

import com.avgmax.trade.domain.Coin;
import com.avgmax.trade.dto.query.CoinWithCreatorQuery;
import com.avgmax.trade.dto.query.CoinWithCreatorWithProfileQuery;
import com.avgmax.trade.dto.query.TradeGroupByCoinQuery;

import java.util.List;
import java.util.Optional;

public interface CoinMapper {
    public int insert(Coin coin);
    public Coin selectByCoinId(String coinId);
    List<CoinWithCreatorQuery> selectAllWithCreator();
    Optional<CoinWithCreatorWithProfileQuery> selectWithCreatorWithProfileById(String coinId);
}
