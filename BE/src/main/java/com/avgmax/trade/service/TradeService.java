package com.avgmax.trade.service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.avgmax.trade.dto.query.CoinWithCreatorWithProfileQuery;
import com.avgmax.trade.dto.query.TradeGroupByCoinQuery;
import com.avgmax.trade.dto.response.TradeFetchResponse;
import com.avgmax.trade.dto.response.TradeSurgingResponse;
import com.avgmax.trade.mapper.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avgmax.global.dto.SuccessResponse;
import com.avgmax.global.exception.ErrorCode;
import com.avgmax.trade.domain.Order;
import com.avgmax.trade.domain.Trade;
import com.avgmax.trade.domain.enums.OrderType;
import com.avgmax.trade.dto.request.OrderRequest;
import com.avgmax.trade.dto.response.OrderResponse;
import com.avgmax.trade.dto.response.TradeSurgingResponse;
import com.avgmax.trade.exception.TradeException;
import com.avgmax.trade.mapper.CoinMapper;
import com.avgmax.user.domain.User;
import com.avgmax.user.exception.UserException;
import com.avgmax.user.mapper.UserMapper;
import com.avgmax.trade.dto.response.ChartResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradeService {
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final CoinMapper coinMapper;
    private final ClosingPriceMapper closingPriceMapper;
    private final TradeMapper tradeMapper;

    @Transactional
    public OrderResponse createOrder(String userId, String coinId, OrderRequest request) {
        Order order = request.toEntity(userId, coinId);
        if (request.getOrderType() == OrderType.BUY) {
            updateUserMoney(userId, OrderType.BUY, request.getOrderTotal());
        }
        orderMapper.insert(order);
        return OrderResponse.from(order);
    }

    @Transactional
    public SuccessResponse cancelOrder(String userId, String coinId, String orderId) {
        Order order = orderMapper.selectByOrderId(orderId)
            .orElseThrow(() -> TradeException.of(ErrorCode.ORDER_NOT_FOUND));
        order.validateOwnership(userId, coinId);
        orderMapper.delete(orderId);
        return SuccessResponse.of(true);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getMyList(String userId, String coinId) {
        List<Order> orders = orderMapper.selectAllByUserId(userId);
        return orders.stream()
            .map(OrderResponse::from)
            .collect(Collectors.toList());
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

    @Transactional(readOnly = true)
    public List<ChartResponse> getChartData(String coinId) {
        return ChartResponse.from(closingPriceMapper.selectBycoinIdDuring180(coinId));
    }

    @Transactional(readOnly = true)
    public TradeFetchResponse getTradeFetch(String coinId) {
        CoinWithCreatorWithProfileQuery coin = coinMapper.selectWithCreatorWithProfileById(coinId)
                .orElseThrow(() -> UserException.of(ErrorCode.COIN_INFO_NOT_FOUND));

        TradeGroupByCoinQuery tradeByCoin = tradeMapper.selectTradeGroupById(coinId)
                .orElse(TradeGroupByCoinQuery.init(coinId));

        return TradeFetchResponse.from(coin, tradeByCoin);
    }
}
