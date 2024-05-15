package com.example.todaysbook.controller;

import com.example.todaysbook.domain.dto.*;
import com.example.todaysbook.domain.entity.User;
import com.example.todaysbook.service.UserService;
import com.example.todaysbook.validate.UserRegisterValidator;
import com.example.todaysbook.validate.UserUpdateAddressValidator;
import com.example.todaysbook.validate.UserUpdateNicknameValidator;
import com.example.todaysbook.validate.UserUpdatePasswordValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PasswordEncoder encoder;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDto request, BindingResult result) {
        if(userService.isExistEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists : " + request.getEmail());
        }

        if(userService.isExistNickName(request.getNickName())) {
            return ResponseEntity.badRequest().body("Nickname already exists : " + request.getNickName());
        }

        UserRegisterValidator validator = new UserRegisterValidator();
        validator.validate(request, result);

        User user = userService.save(request);
        UserResponseDto response = user.toResponse();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PutMapping ("/update/nickname")
    public ResponseEntity<?> updateUserNickname(@RequestBody UserRequestDto request, @AuthenticationPrincipal CustomUserDetails userDetails, BindingResult result) {
        String userNickname = userDetails.getNickname();

        if(userNickname.equals(request.getNickName())) {
            return ResponseEntity.badRequest().body("Same Nickname: " + userNickname);
        }

        UserUpdateNicknameValidator validator = new UserUpdateNicknameValidator();
        validator.validate(request, result);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.updateNickname(userDetails.getUserId(), request.getNickName());

        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), authentication.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(newAuthentication);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @PutMapping ("/update/addressInfo")
    public ResponseEntity<?> updateUserAddressInfo(@RequestBody UserRequestDto request, @AuthenticationPrincipal CustomUserDetails userDetails, BindingResult result) {
        UserUpdateAddressValidator validator = new UserUpdateAddressValidator();
        validator.validate(request, result);

        userService.updateAddressInfoById(userDetails.getUserId(), request.getAddress(), request.getZipcode());

        return ResponseEntity.ok().build();
    }

    @Transactional
    @PutMapping ("/update/password")
    public ResponseEntity<?> updateUserPassword(@RequestBody UserRequestDto request, @AuthenticationPrincipal CustomUserDetails userDetails, BindingResult result) {
        UserUpdatePasswordValidator validator = new UserUpdatePasswordValidator();
        validator.validate(request, result);

        String password = encoder.encode(request.getPassword());

        userService.updatePassword(userDetails.getUserId(), password);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<?> withdrawUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.withdraw(userDetails.getUserId());

        return ResponseEntity.ok().build();
    }
}
