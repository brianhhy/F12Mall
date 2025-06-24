package com.avgmax.user.mapper;

import com.avgmax.user.domain.User;

public interface UserMapper {
    public int insert(User user);
    public User selectByUsername(String username);
}
