package com.avgmax.user.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.stereotype.Service;

// import com.avgmax.trade.domain.Trade;
import com.avgmax.trade.domain.enums.OrderType;
import com.avgmax.trade.domain.enums.TradeStatus;
import com.avgmax.trade.dto.response.TradeUserCoinResponse;
import com.avgmax.trade.mapper.TradeMapper;
import com.avgmax.user.domain.Career;
import com.avgmax.user.domain.Certification;
import com.avgmax.user.domain.Education;
//import com.avgmax.user.domain.Profile;
import com.avgmax.user.domain.UserSkill;
import com.avgmax.user.domain.User;

import com.avgmax.user.mapper.UserMapper;
import com.avgmax.user.mapper.CareerMapper;
import com.avgmax.user.mapper.EducationMapper;
//import com.avgmax.user.mapper.ProfileMapper;
import com.avgmax.user.mapper.UserSkillMapper;
import com.avgmax.trade.mapper.UserCoinMapper;
import com.avgmax.user.mapper.CertificationMapper;
//import com.avgmax.user.dto.data.LinkData;
import com.avgmax.user.dto.query.UserCoinWithCoinWithCreatorQuery;
import com.avgmax.user.dto.query.UserSkillWithSkillQuery;
import com.avgmax.user.dto.request.CareerRequest;
import com.avgmax.user.dto.request.CertificationRequest;
import com.avgmax.user.dto.request.EducationRequest;
import com.avgmax.user.dto.request.UserProfileUpdateRequest;
import com.avgmax.user.dto.response.UserCoinResponse;
import com.avgmax.user.dto.response.UserInformResponse;
import com.avgmax.user.dto.response.UserProfileUpdateResponse;

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

    public UserInformResponse getUserInform(String userId){
        User user = userMapper.selectByUserId(userId)
            .orElseThrow(() -> UserException.of(ErrorCode.USER_NOT_FOUND));
        //Profile profile = profileMapper.selectByUserId(userId);
        List<Career> careerList = careerMapper.selectByUserId(userId);
        List<Education> educationList = educationMapper.selectByUserId(userId);
        List<Certification> certificationList = certificationMapper.selectByUserId(userId);
        List<UserSkillWithSkillQuery> userSkillList = userSkillMapper.selectByUserId(userId);
        
        return UserInformResponse.from(user, careerList, educationList, certificationList, userSkillList);
    }

    public List<UserCoinResponse> getUserCoinList(String userId){
        
        List<TradeUserCoinResponse> tradeResponses = tradeMapper.selectByUserId(userId).stream()
            .map(TradeUserCoinResponse::from)
            .collect(Collectors.toList());

        Map<String, List<TradeUserCoinResponse>> tradeMapByCoinId = tradeResponses.stream()
            .collect(Collectors.groupingBy(TradeUserCoinResponse::getCoinId));

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

    public UserProfileUpdateResponse updateUserProfile(String userId, UserProfileUpdateRequest request){
        User user = updateUser(userId, request.getName(), request.getEmail(), request.getUsername(), request.getPwd(), request.getImage());
        userMapper.update(user);
        
        //Profile profile = updateProfile(userId, request.getBio(), request.getPosition(), request.getLink(), request.getResume());
        //profileMapper.update(profile);
        
        List<CareerRequest> careerRequests = request.getCareer();
        careerMapper.deleteByUserId(userId);
        careerRequests.stream()
            .map(careerRequest -> careerRequest.toEntity(userId))
            .forEach(career -> careerMapper.insert(career));
        
        List<CertificationRequest> certificationRequests = request.getCertificateUrl();
        careerMapper.deleteByUserId(userId);
        certificationRequests.stream()
            .map(certificationRequest -> certificationRequest.toEntity(userId))
            .forEach(certification -> certificationMapper.insert(certification));
        
        List<EducationRequest> educationnRequests = request.getEducation();
        educationMapper.deleteByUserId(userId);
        educationnRequests.stream()
            .map(educationRequest -> educationRequest.toEntity(userId))
            .forEach(education -> educationMapper.insert(education));
        
        List<String> stackList = request.getStack();
        List<String> skillIds = userSkillMapper.selectByStack(stackList);
        List<UserSkill> userSkills = skillIds.stream()
            .map(skillId -> UserSkill.of(userId, skillId))
            .collect(Collectors.toList());
        
        userSkillMapper.deleteByUserId(userId);
        userSkills.forEach(userSkillMapper::insert);

        return UserProfileUpdateResponse.of(true);
    }

    private User updateUser(String userId, String name, String email, String username, String pwd, String image) {
        User user = userMapper.selectByUserId(userId)
            .orElseThrow(() -> UserException.of(ErrorCode.USER_NOT_FOUND));
        user.updateIfChanged(name, email, username, pwd, image);
        userMapper.update(user);
        return user;
    }

    // private Profile updateProfile(String userId, String bio, String position, LinkData link, String resume){
    //     Profile profile = profileMapper.selectByUserId(userId);
    //     profile.updateIfChanged(position, bio, link, resume);
    //     return profile;
    // }

    private BigDecimal calculateHoldQuantity(List<TradeUserCoinResponse> trades){
        BigDecimal holdQuantity = trades.stream()
        .filter(trade -> trade.getStatus() == TradeStatus.COMPLETED)
        .map(trade -> 
            trade.getOrderType() == OrderType.SELL 
                ? trade.getQuantity().negate()
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
            .map(trade -> trade.getQuantity().multiply(trade.getUnitPrice()))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateBuyPrice(List<TradeUserCoinResponse> trades){
        BigDecimal holdQuantity = calculateHoldQuantity(trades);
        BigDecimal totalBuyAmount = calculateTotalBuyAmount(trades);

        return totalBuyAmount.divide(holdQuantity, 2, RoundingMode.HALF_UP);
    }
}
