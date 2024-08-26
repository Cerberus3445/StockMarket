package com.cerberus.userservice.controller;

import com.cerberus.userservice.dto.UserDto;
import com.cerberus.userservice.exception.ApplicationExceptions;
import com.cerberus.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public Mono<UserDto> getById(@PathVariable("id") Long id){
        return this.userService.getById(id);
    }

    @GetMapping("/find/{usernameOrEmail}")
    public Mono<UserDto> getByEmail(@PathVariable("usernameOrEmail") String email){
        return this.userService.getByUsernameOrEmail(email);
    }

    @PostMapping
    public Mono<Void> create(@RequestBody Mono<UserDto> dtoMono){
        return this.userService.create(dtoMono);
    }

    @PatchMapping("/{id}")
    public Mono<UserDto> update(@PathVariable("id") Long id, @RequestBody Mono<UserDto> dtoMono){
        return this.userService.update(id, dtoMono);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") Long id){
        return this.userService.delete(id)
                .filter( i -> i)
                .switchIfEmpty(ApplicationExceptions.userNotFound(id))
                .then();
    }
}
