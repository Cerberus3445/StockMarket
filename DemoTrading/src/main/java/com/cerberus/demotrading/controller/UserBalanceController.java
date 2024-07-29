package com.cerberus.demotrading.controller;

import com.cerberus.demotrading.dto.UserBalanceDto;
import com.cerberus.demotrading.service.UserBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/trade/user/{userId}")
@RequiredArgsConstructor
public class UserBalanceController {

    private final UserBalanceService userBalanceService;

    @GetMapping
    public Mono<UserBalanceDto> get(@PathVariable("userId") Integer userId){
        return this.userBalanceService.get(userId);
    }

    @PostMapping
    public Mono<UserBalanceDto> create(@PathVariable("userId") Integer userId){
        return this.userBalanceService.create(userId);
    }

    @PostMapping("/increase")
    public Mono<UserBalanceDto> increase(@PathVariable("userId") Integer userId, @RequestParam("sum") Double sum){
        return this.userBalanceService.increase(userId, sum);
    }
}
