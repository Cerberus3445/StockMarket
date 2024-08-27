package com.cerberus.stockmarket.filter;

import com.cerberus.stockmarket.service.AuthTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Order(1) //первый в списке фильтров
@Component
@RequiredArgsConstructor
public class AuthenticationWebFilter implements WebFilter {

    private final AuthTokenService authTokenService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("Auth-Token");
        return this.authTokenService.getByValue(token)
                .switchIfEmpty(Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
                .doOnNext(i -> exchange.getAttributes().put("category", i.getCategory()))
                .flatMap(i -> chain.filter(exchange));
    }
}
