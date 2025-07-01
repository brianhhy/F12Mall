package com.avgmax.trade.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avgmax.global.dto.SuccessResponse;
import com.avgmax.trade.dto.request.TradeRequest;
import com.avgmax.trade.dto.response.TradeResponse;
import com.avgmax.trade.service.TradeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/coins")
@RequiredArgsConstructor
public class TradeController {
    private final TradeService tradeService;

    // 주문 요청
    @PostMapping("/{coinId}/trades")
    public ResponseEntity<TradeResponse> createOrder(HttpSession session, @PathVariable String coinId, @RequestBody TradeRequest request) {
        String userId = (String) session.getAttribute("user");
        log.info("POST 주문 요청: {}", userId);
        return ResponseEntity.ok(tradeService.createOrder(userId, coinId, request));
    }

    // 주문 취소
    @DeleteMapping("/{coinId}/trades/{tradeId}")
    public ResponseEntity<SuccessResponse> cancelOrder(HttpSession session, @PathVariable String coinId, @PathVariable String tradeId) {
        String userId = (String) session.getAttribute("user");
        log.info("DELETE 주문 취소: {}", userId);
        return ResponseEntity.ok(tradeService.cancelOrder(userId, coinId, tradeId));
    }
}
