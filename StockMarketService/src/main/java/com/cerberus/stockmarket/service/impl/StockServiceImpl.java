package com.cerberus.stockmarket.service.impl;

import com.cerberus.stockmarket.dto.StockDto;
import com.cerberus.stockmarket.exception.ApplicationExceptions;
import com.cerberus.stockmarket.mapper.StockMapper;
import com.cerberus.stockmarket.repository.StockRepository;
import com.cerberus.stockmarket.service.StockService;
import com.cerberus.stockmarket.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    @Override
    public Mono<StockDto> getByTicker(String ticker){
        log.info("getByTicker {}", ticker);
        return this.stockRepository.findByTicker(ticker)
                .switchIfEmpty(ApplicationExceptions.stockNotFound(ticker))
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
        log.info("create");
        return dtoMono
                .transform(RequestValidator.validate())
                .map(StockMapper::toEntity)
                .flatMap(this.stockRepository::save)
                .map(StockMapper::toDto);
    }

    @Override
    public Flux<StockDto> getWithPagination(Integer page, Integer size) {
        log.info("getWithPagination page: {}, size: {}", page, size);
        return this.stockRepository.findBy(PageRequest.of(page - 1 , size))
                .map(StockMapper::toDto);
    }

    @Override
    public Mono<StockDto> update(Integer id, Mono<StockDto> dtoMono) {
        return this.stockRepository.findById(id)
                .switchIfEmpty(ApplicationExceptions.stockNotFound(id))
                .flatMap(mono -> dtoMono)
                .transform(RequestValidator.validate())
                .doOnNext(i -> log.info("update id: {}, object: {}", id, i))
                .map(StockMapper::toEntity)
                .doOnNext(i -> i.setId(id))
                .flatMap(this.stockRepository::save)
                .map(StockMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Integer id) {
        log.info("delete {}", id);
        return this.stockRepository.deleteWithId(id)
                .switchIfEmpty(ApplicationExceptions.stockNotFound(id))
                .then();
    }

}
