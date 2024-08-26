package com.cerberus.userservice.filter;

import com.cerberus.userservice.dto.AuthTokenDto;
import com.cerberus.userservice.service.AuthTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Order(1) //первый в списке фильтров
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationWebFilter implements WebFilter {

    private final AuthTokenService authTokenService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("Auth-Token");
        return this.authTokenService.getByValue(token)
                .defaultIfEmpty(new AuthTokenDto(null, null, null, Category.NO_TOKEN))
                .doOnNext(i -> {
                    if(i.getCategory() != Category.NO_TOKEN){
                        exchange.getAttributes().put("category", i.getCategory());
                    } else {
                        exchange.getAttributes().put("category", Category.NO_TOKEN);
                    }

                }).flatMap(i -> chain.filter(exchange));
    }
}
