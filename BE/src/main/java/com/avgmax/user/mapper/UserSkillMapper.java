package com.avgmax.user.mapper;

import com.avgmax.user.domain.UserSkill;
import java.util.List;

public interface UserSkillMapper {
    public int insert(UserSkill userSkill);
    public List<UserSkill> selectByUserId(String userId);
}
