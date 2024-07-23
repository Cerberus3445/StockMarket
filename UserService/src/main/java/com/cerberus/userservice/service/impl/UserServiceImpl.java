package com.cerberus.userservice.service.impl;

import com.cerberus.userservice.dto.UserDto;
import com.cerberus.userservice.exception.ApplicationExceptions;
import com.cerberus.userservice.mapper.UserMapper;
import com.cerberus.userservice.model.Role;
import com.cerberus.userservice.repository.UserRepository;
import com.cerberus.userservice.service.UserService;
import com.cerberus.userservice.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserDto> getById(Integer id){
        return this.userRepository.findById(id)
                .switchIfEmpty(ApplicationExceptions.userNotFound(id))
                .map(UserMapper::toDto);
    }

    @Override
    public Mono<UserDto> getByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .switchIfEmpty(ApplicationExceptions.userNotFound(email))
                .map(UserMapper::toDto);
    }

    @Override
    public Mono<UserDto> create(Mono<UserDto> dtoMono) {
        return dtoMono.transform(RequestValidator.validate())
                .map(UserMapper::toEntity)
                .doOnNext(user -> user.setRole(Role.ROLE_USER))
                .flatMap(this.userRepository::save)
                .map(UserMapper::toDto);
    }

    @Override
    public Mono<UserDto> update(Integer id, Mono<UserDto> dtoMono) {
        return this.userRepository.findById(id)
                .switchIfEmpty(ApplicationExceptions.userNotFound(id))
                .flatMap(user -> dtoMono)
                .transform(RequestValidator.validate())
                .map(UserMapper::toEntity)
                .doOnNext(user -> user.setId(id))
                .flatMap(this.userRepository::save)
                .map(UserMapper::toDto);
    }

    @Override
    public Mono<Boolean> delete(Integer id) {
        return this.userRepository.deleteWithId(id);
    }
}
