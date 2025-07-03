package com.avgmax.trade.service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.avgmax.trade.dto.response.TradeSurgingResponse;
import com.avgmax.trade.mapper.CoinMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avgmax.global.dto.SuccessResponse;
import com.avgmax.trade.domain.Trade;
import com.avgmax.trade.domain.enums.OrderType;
import com.avgmax.trade.dto.request.TradeRequest;
import com.avgmax.trade.dto.response.TradeResponse;
import com.avgmax.trade.mapper.TradeMapper;
import com.avgmax.user.domain.User;
import com.avgmax.global.exception.ErrorCode;
import com.avgmax.trade.exception.TradeException;
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
    private final CoinMapper coinMapper;

    @Transactional
    public TradeResponse createOrder(String userId, String coinId, TradeRequest request) {
        Trade trade = request.toEntity(userId, coinId);
        if (request.getOrderType() == OrderType.BUY) {
            updateUserMoney(userId, OrderType.BUY, request.getOrderTotal());
        }
        tradeMapper.insert(trade);
        return TradeResponse.from(trade);
    }

    @Transactional
    public SuccessResponse cancelOrder(String userId, String coinId, String tradeId) {
        Trade trade = tradeMapper.selectByTradeId(tradeId)
            .orElseThrow(() -> TradeException.of(ErrorCode.TRADE_NOT_FOUND));
        trade.validateOwnership(userId, coinId);
        tradeMapper.delete(tradeId);
        return SuccessResponse.of(true);
    }

    private User updateUserMoney(String userId, OrderType orderType, BigDecimal amount) {
        User user = userMapper.selectByUserId(userId)
            .orElseThrow(() -> UserException.of(ErrorCode.USER_NOT_FOUND));
        user.processOrderAmount(orderType, amount);
        userMapper.updateMoney(user);
        return user;
    }

    @Transactional(readOnly = true)
    public List<TradeSurgingResponse> getSurgingCoins() {
        return coinMapper.selectAllWithCreator().stream()
                .map(TradeSurgingResponse::from)
                .sorted(Comparator.comparing(TradeSurgingResponse::getFluctuationRate).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}
