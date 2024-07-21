package com.cerberus.stockmarket.service.impl;

import com.cerberus.stockmarket.dto.StockDto;
import com.cerberus.stockmarket.exceptions.ApplicationExceptions;
import com.cerberus.stockmarket.mapper.StockMapper;
import com.cerberus.stockmarket.repository.StockRepository;
import com.cerberus.stockmarket.service.StockService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    private final static Logger log = LoggerFactory.getLogger(StockServiceImpl.class);

    @Override
    public Mono<StockDto> getByTicker(String ticker){
        log.info("getByTicker {}", ticker);
        return this.stockRepository.findByTicker(ticker)
                .map(StockMapper::toDto);
    }

    @Override
    public Mono<StockDto> getById(Integer id) {
        log.info("getById {}", id);
        return this.stockRepository.findById(id)
                .switchIfEmpty(ApplicationExceptions.stockNotFound(id))
                .map(StockMapper::toDto);
    }

    @Override
    public Flux<StockDto> getAll(){
        log.info("getAll");
        return this.stockRepository.findAll()
                .map(StockMapper::toDto)
                .doOnNext(i -> log.info("выполнено"));
    }

    @Override
    public Mono<StockDto> create(Mono<StockDto> dtoMono){
        return dtoMono
                .doOnNext(i -> log.info("create {}", i))
                .map(StockMapper::toEntity)
                .flatMap(this.stockRepository::save)
                .map(StockMapper::toDto);
    }

    @Override
    public Flux<StockDto> getByPage(Integer page, Integer size) {
        log.info("getByPage page: {}, size: {}", page, size);
        return this.stockRepository.findBy(PageRequest.of(page -1 , size))
                .map(StockMapper::toDto);
    }

    @Override
    public Mono<StockDto> update(Integer id, Mono<StockDto> dtoMono) {
        return this.stockRepository.findById(id)
                .switchIfEmpty(ApplicationExceptions.stockNotFound(id))
                .flatMap(mono -> dtoMono)
                .doOnNext(i -> log.info("update id: {}, object: {}", id, i))
                .map(StockMapper::toEntity)
                .doOnNext(i -> i.setId(id))
                .flatMap(this.stockRepository::save)
                .map(StockMapper::toDto);
    }

    @Override
    public Mono<Boolean> delete(Integer id) {
        log.info("delete {}", id);
        return this.stockRepository.deleteWithId(id)
                .switchIfEmpty(ApplicationExceptions.stockNotFound(id));
    }

}
