package com.cerberus.stockmarket.validator;

import com.cerberus.stockmarket.client.StockClient;
import com.cerberus.stockmarket.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class DataSetupService implements CommandLineRunner { //автоматический запуск при старте приложения

    private final StockClient stockClient;

    private final StockService stockService;

    @Override
    public void run(String... args) throws Exception {
        stockService.getAll()
                .delayElements(Duration.ofSeconds(3))
                .flatMap(i -> stockClient.getPrice(i.ticker()))
                .subscribe();
    }
}
