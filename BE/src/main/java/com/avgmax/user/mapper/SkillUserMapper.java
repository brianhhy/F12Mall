package com.avgmax.user.mapper;

import com.avgmax.user.domain.SkillUser;
import java.util.List;

public interface SkillUserMapper {
    public int insert(SkillUser skillUser);
    public List<SkillUser> selectByUserId(String userId);
}
