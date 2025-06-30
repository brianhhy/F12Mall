package com.avgmax.trade.mapper;

import com.avgmax.trade.domain.UserCoin;

public interface UserCoinMapper {
    public int insert(UserCoin userCoin);
    public UserCoin selectByHolderIdAndCoinId(String holderId, String coinId);
}
