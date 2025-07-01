package com.avgmax.user.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

// import com.avgmax.user.domain.Profile;
// import com.avgmax.trade.domain.Trade;
import com.avgmax.trade.domain.enums.OrderType;
import com.avgmax.trade.domain.enums.TradeStatus;
import com.avgmax.trade.dto.response.TradeUserCoinResponse;
import com.avgmax.trade.mapper.TradeMapper;
import com.avgmax.user.domain.Career;
import com.avgmax.user.domain.Certification;
import com.avgmax.user.domain.Education;
import com.avgmax.user.domain.UserSkill;
import com.avgmax.user.domain.User;

import com.avgmax.user.mapper.UserMapper;
import com.avgmax.user.mapper.CareerMapper;
import com.avgmax.user.mapper.EducationMapper;
//import com.avgmax.user.mapper.ProfileMapper;
import com.avgmax.user.mapper.UserSkillMapper;
import com.avgmax.trade.mapper.UserCoinMapper;
import com.avgmax.user.mapper.CertificationMapper;
import com.avgmax.user.dto.query.UserCoinWithCoinWithCreatorQuery;
import com.avgmax.user.dto.response.UserCoinResponse;
import com.avgmax.user.dto.response.UserInformResponse;

import lombok.RequiredArgsConstructor;
import com.avgmax.global.exception.ErrorCode;
import com.avgmax.user.exception.UserException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    //private final ProfileMapper profileMapper;
    private final CareerMapper careerMapper;
    private final EducationMapper educationMapper;
    private final CertificationMapper certificationMapper;
    private final UserSkillMapper userSkillMapper;
    private final UserCoinMapper userCoinMapper;
    private final TradeMapper tradeMapper;

    //사용자 정보 조회 
    public UserInformResponse getUserInform(String userId){
        User user = userMapper.selectByUserId(userId)
            .orElseThrow(() -> UserException.of(ErrorCode.USER_NOT_FOUND));
        //Profile profile = profileMapper.selectByUserId(userId);
        List<Career> careerList = careerMapper.selectByUserId(userId);
        List<Education> educationList = educationMapper.selectByUserId(userId);
        List<Certification> certificationList = certificationMapper.selectByUserId(userId);
        List<UserSkill> userSkillList = userSkillMapper.selectByUserId(userId);
        
        return UserInformResponse.from(user, careerList, educationList, certificationList, userSkillList);
    }

    //사용자 보유 코인 목록 조회
    public List<UserCoinResponse> getUserCoinList(String userId){
        //거래내역 조회 -> DTO 변환
        List<TradeUserCoinResponse> tradeResponses = tradeMapper.selectByUserId(userId).stream()
            .map(TradeUserCoinResponse::from)
            .collect(Collectors.toList());

        //coinId로 거래들 묶음
        Map<String, List<TradeUserCoinResponse>> tradeMapByCoinId = tradeResponses.stream()
            .collect(Collectors.groupingBy(TradeUserCoinResponse::getCoinId));

        //보유 코인 목록 조회
        List<UserCoinWithCoinWithCreatorQuery> userCoinQueries = userCoinMapper.selectByUserId(userId);

        List<UserCoinResponse> responses = userCoinQueries.stream()
            .map(userCoinQuery -> {
                    String coinId = userCoinQuery.getCoinId(); 
                    List<TradeUserCoinResponse> trades = tradeMapByCoinId.getOrDefault(coinId, Collections.emptyList());

                    //총보유수량 = 총보유수량 + status가 completed인 
                    BigDecimal holdQuantity = calculateHoldQuantity(trades);
                    // BigDecimal currentPrice = SSE
                    BigDecimal sellableQuantity = calculateSellableQuantity(trades);
                    BigDecimal buyPrice = calculateBuyPrice(trades);
                    BigDecimal totalBuyAmount = calculateTotalBuyAmount(trades);
                    // BigDecimal valuationRate = currentPrice.subtract(buyPrice).divide(buyPrice, 6, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP); 

                    UserCoinResponse response = UserCoinResponse.from(userCoinQuery)
                        .holdQuantity(holdQuantity)
                        // .currentPrice(currentPrice)
                        .sellableQuantity(sellableQuantity)
                        .buyPrice(buyPrice)
                        .totalBuyAmount(totalBuyAmount)
                        // .valuaionRate(valuationRate)
                        .build();
                    return response;
                })
            .collect(Collectors.toList());
        return responses;
    }

    private BigDecimal calculateHoldQuantity(List<TradeUserCoinResponse> trades){
        BigDecimal holdQuantity = trades.stream()
        .filter(trade -> trade.getStatus() == TradeStatus.COMPLETED)
        .map(trade -> 
            trade.getOrderType() == OrderType.SELL 
                ? trade.getQuantity().negate() //SELL이면 음수로
                : trade.getQuantity())
        .reduce(BigDecimal.ZERO, BigDecimal::add);

        return holdQuantity;
    }

    private BigDecimal calculateSellableQuantity(List<TradeUserCoinResponse> trades){
        BigDecimal holdQuantity = calculateHoldQuantity(trades);

        BigDecimal lockedQuantity = trades.stream()
        .filter(trade -> trade.getOrderType() == OrderType.SELL)
        .filter(trade -> trade.getStatus() == TradeStatus.PENDING || trade.getStatus() == TradeStatus.COMPLETED)
        .map(TradeUserCoinResponse::getQuantity)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

        return holdQuantity.subtract(lockedQuantity);
    }

   private BigDecimal calculateTotalBuyAmount(List<TradeUserCoinResponse> trades) {
        return trades.stream()
            .filter(trade ->
                trade.getStatus() == TradeStatus.COMPLETED &&
                trade.getOrderType() == OrderType.BUY)
            .map(trade -> trade.getQuantity().multiply(trade.getUnitPrice())) // 수량 × 단가
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateBuyPrice(List<TradeUserCoinResponse> trades){
        BigDecimal holdQuantity = calculateHoldQuantity(trades);
        BigDecimal totalBuyAmount = calculateTotalBuyAmount(trades);

        return totalBuyAmount.divide(holdQuantity, 2, RoundingMode.HALF_UP);
    }
}
