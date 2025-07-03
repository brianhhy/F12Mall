package com.avgmax.trade.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;

import com.avgmax.trade.domain.Order;
import com.avgmax.trade.dto.query.OrderWithUserAndCoinQuery;

public interface OrderMapper {
    void insert(Order order);
    Optional<Order> selectByOrderId(String orderId);
    Optional<OrderWithUserAndCoinQuery> selectByOrderIdWithUserAndCoin(String orderId);
    List<Order> selectAllByUserId(String userId);
    List<Order> selectAllByUserIdAndCoinId(@Param("userId") String userId, @Param("coinId") String coinId);
    void delete(String orderId);
}
