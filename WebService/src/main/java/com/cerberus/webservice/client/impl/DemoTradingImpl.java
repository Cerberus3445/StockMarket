package com.cerberus.webservice.client.impl;

import com.cerberus.webservice.client.DemoTrading;
import com.cerberus.webservice.dto.UserBalanceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class DemoTradingImpl implements DemoTrading {


    @Override
    public Mono<UserBalanceDto> getBalance(Integer userId) {
        return null;
    }
}
