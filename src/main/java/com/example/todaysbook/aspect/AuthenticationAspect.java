package com.example.todaysbook.aspect;

import com.example.todaysbook.domain.dto.CustomUserDetails;
import com.example.todaysbook.domain.entity.User;
import com.example.todaysbook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthenticationAspect {

    private final UserService userService;

    @Before("execution(* com.example.todaysbook.controller.*.*(..))")
    public void addUserNameToModel(JoinPoint joinPoint) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            for (Object arg : joinPoint.getArgs()) {
                if (arg instanceof Model) {
                    Model model = (Model) arg;

                    model.addAttribute("role", userDetails.getRole());
                    model.addAttribute("loginUserId", userDetails.getUserId());
                    model.addAttribute("mileage", userDetails.getMileage());
                    model.addAttribute("nickname", userDetails.getNickname());

                    break;
                }
            }
        }
    }

    @Pointcut("execution(* com.example.todaysbook.controller.*.*(..))")
    public void controllerMethods() {}

    @After("controllerMethods()")
    public void reloadUserInfo(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                Long userId = ((CustomUserDetails) principal).getUserId();

                for (Object arg : joinPoint.getArgs()) {
                    if (arg instanceof Model) {
                        Model model = (Model) arg;

                        User user = userService.getUserByUserId(userId);

                        model.addAttribute("nickname", user.getNickName());
                        model.addAttribute("mileage", user.getMileage());
                        model.addAttribute("role", user.getRole());

                        break;
                    }
                }
            }
        }
    }
}