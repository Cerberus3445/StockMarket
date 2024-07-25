package com.cerberus.webservice.client;

import com.cerberus.webservice.dto.StockPriceDto;
import reactor.core.publisher.Flux;

public interface StockClient {

    Flux<StockPriceDto> getStocksPricesWithPagination(Integer page, Integer size);
}
