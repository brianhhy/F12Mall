package com.avgmax.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.avgmax.user.dto.response.UserInformResponse;
import com.avgmax.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
   private final UserService userService;

   @GetMapping("/{userId}/profile") // /{userId}/profile 요청
   public ResponseEntity<UserInformResponse> getUserInform(
    @PathVariable String userId //url 경로로 넘어온 데이터 값 저장
   ){
        //userId를 userService로 전달 하여 response 받는다
        UserInformResponse response = userService.getUserInform(userId);
        return ResponseEntity.ok(response); //RestController로 인해 response가 자바객체 -> JSON 반환
   }
}
