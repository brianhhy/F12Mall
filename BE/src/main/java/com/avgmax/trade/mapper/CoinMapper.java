package com.avgmax.trade.mapper;

import com.avgmax.trade.domain.Coin;
import com.avgmax.trade.dto.query.CoinWithCreatorQuery;
import com.avgmax.trade.dto.query.CoinWithCreatorWithProfileQuery;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;

public interface CoinMapper {
    public int insert(Coin coin);
    public Coin selectByCoinId(String coinId);
    List<CoinWithCreatorQuery> selectAllWithCreator();
    Optional<CoinWithCreatorWithProfileQuery> selectWithCreatorWithProfileById(String coinId);
    public int updateCurrentPrice(@Param("coinId") String coinId, @Param("currentPrice") BigDecimal currentPrice);
}
