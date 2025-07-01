package com.avgmax.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.avgmax.user.dto.request.UserProfileUpdateRequest;
import com.avgmax.user.dto.response.UserCoinResponse;
import com.avgmax.user.dto.response.UserInformResponse;
import com.avgmax.user.dto.response.UserLogoutResponse;
import com.avgmax.user.dto.response.UserProfileUpdateResponse;
import com.avgmax.user.service.UserService;
import com.avgmax.user.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
   private final UserService userService;
   private final FileService fileService;

   @GetMapping("/{userId}/profile")
   public ResponseEntity<UserInformResponse> getUserInform(@PathVariable String userId) {
         UserInformResponse response = userService.getUserInform(userId);
         return ResponseEntity.ok(response);
   }
   @PostMapping("/{userId}/uploads")
   public ResponseEntity<List<String>> uploadFiles(
         @PathVariable String userId,
         @RequestParam("file") List<MultipartFile> files) {

      List<String> urls = fileService.uploadForUser(userId, files);
      return ResponseEntity.ok(urls);
   }
   //보유 코인 목록 조회
   @GetMapping("/{userId}/coins")
   public ResponseEntity<List<UserCoinResponse>> getUserCoinList(@PathVariable String userId){
      List<UserCoinResponse> responses = userService.getUserCoinList(userId);
      return ResponseEntity.ok(responses);
   }
   //사용자 정보 수정
   @PutMapping("/{userId}/profile")
   public ResponseEntity<UserProfileUpdateResponse> updateUserProfile(
         @PathVariable String userId,
         @RequestBody UserProfileUpdateRequest request
      ){
         UserProfileUpdateResponse response = userService.updateUserProfile(userId, request);
         return ResponseEntity.ok(response);
   }
}
