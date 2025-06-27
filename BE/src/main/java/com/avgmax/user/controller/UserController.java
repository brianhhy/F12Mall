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

   @GetMapping("/{userId}/profile")
   public ResponseEntity<UserInformResponse> getUserInform(@PathVariable String userId){
        UserInformResponse response = userService.getUserInform(userId);
        return ResponseEntity.ok(response);
   }
}
