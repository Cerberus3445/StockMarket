package com.cerberus.webservice.client;

import com.cerberus.webservice.dto.UserBalanceDto;
import reactor.core.publisher.Mono;

public interface DemoTrading {

    Mono<UserBalanceDto> getBalance(Integer userId);
}
