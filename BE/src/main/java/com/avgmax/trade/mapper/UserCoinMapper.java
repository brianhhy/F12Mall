package com.avgmax.trade.mapper;

import java.util.List;

import com.avgmax.trade.domain.UserCoin;
import com.avgmax.user.dto.query.UserCoinWithCoinWithCreatorQuery;

public interface UserCoinMapper {
    public int insert(UserCoin userCoin);
    public UserCoin selectByHolderIdAndCoinId(String holderId, String coinId);
    public List<UserCoinWithCoinWithCreatorQuery> selectByUserId(String userId);
}
