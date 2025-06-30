package com.avgmax.user.mapper;

import java.util.List;

import com.avgmax.user.dto.query.UserCoinWithCoinWithCreatorQuery;

public interface UserCoinMapper {
    public List<UserCoinWithCoinWithCreatorQuery> selectByUserId(String userId);
} 