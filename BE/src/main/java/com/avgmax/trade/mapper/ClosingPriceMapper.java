package com.avgmax.trade.mapper;

import com.avgmax.trade.domain.ClosingPrice;
import java.util.List;

public interface ClosingPriceMapper {
    public int insert(ClosingPrice closingPrice);
    public List<ClosingPrice> selectBycoinId(String coinId);
    public List<ClosingPrice> selectBycoinIdDuring180(String coinId);
}
