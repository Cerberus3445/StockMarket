package com.cerberus.stockmarket.filter;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Order(2)
@Component
public class AuthorizationWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var category = exchange.getAttributes().get("category");
        return switch (category){
            case Category.STANDARD -> standardUser(exchange, chain);
            case Category.PRIME -> primeUser(exchange, chain);
            case Category.ADMIN -> adminUser(exchange, chain);
            default -> throw new IllegalStateException("Unexpected value: " + category);
        };
    }

    private Mono<Void> adminUser(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange);
    }

    private Mono<Void> primeUser(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        if(!path.startsWith("/api/v1/admin")){
            return chain.filter(exchange);
        } else {
            return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN));
        }
    }

    private Mono<Void> standardUser(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        var isGet = HttpMethod.GET.equals(exchange.getRequest().getMethod());
        if(!path.startsWith("/api/v1/stocks/prime") && isGet){
            return chain.filter(exchange);
        } else {
            return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN));
        }
    }
}
