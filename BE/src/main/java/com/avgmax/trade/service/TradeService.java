package com.avgmax.trade.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avgmax.trade.domain.Trade;
import com.avgmax.trade.domain.enums.OrderType;
import com.avgmax.trade.dto.request.TradeRequest;
import com.avgmax.trade.dto.response.TradeResponse;
import com.avgmax.trade.mapper.TradeMapper;
import com.avgmax.user.domain.User;
import com.avgmax.global.exception.ErrorCode;
import com.avgmax.user.exception.UserException;
import com.avgmax.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradeService {
    private final TradeMapper tradeMapper;
    private final UserMapper userMapper;

    @Transactional
    public TradeResponse createOrder(String userId, String coinId, TradeRequest request) {
        Trade trade = request.toEntity(userId, coinId);
        if (request.getOrderType() == OrderType.BUY) {
            updateUserMoney(userId, OrderType.BUY, request.getOrderTotal());
        }
        tradeMapper.insert(trade);
        return TradeResponse.from(trade);
    }

    private User updateUserMoney(String userId, OrderType orderType, BigDecimal amount) {
        User user = userMapper.selectByUserId(userId)
            .orElseThrow(() -> UserException.of(ErrorCode.USER_NOT_FOUND));
        user.processOrderAmount(orderType, amount);
        userMapper.updateMoney(user);
        return user;
    }

}
