package com.cerberus.webservice;

import com.cerberus.webservice.client.UserClient;
import com.cerberus.webservice.dto.UserDto;
import com.cerberus.webservice.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
@SpringBootTest
public class UserClientTest {

    @Autowired
    private UserClient userClient;

    @Test
    public void getById(){
        this.userClient.getById(7)
                .doOnNext(userDto -> log.info("получено: {}", userDto))
                .as(StepVerifier::create)
                .assertNext(userDto -> Assertions.assertEquals("stockUser", userDto.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    public void getByEmail(){
        this.userClient.getByEmail("stockUser@gmail.com")
                .doOnNext(userDto -> log.info("получено: {}", userDto))
                .as(StepVerifier::create)
                .assertNext(userDto -> Assertions.assertEquals("stockUser@gmail.com", userDto.getEmail()))
                .expectComplete()
                .verify();
    }

    @Test
    public void create(){
        var userDto = Mono.just(new UserDto(null, "Trump", "makeAmericaGreatAgain@gmail.com", Role.ROLE_ADMIN));
        this.userClient.create(userDto)
                .doOnNext(i -> log.info("получено: {}", userDto))
                .as(StepVerifier::create)
                .assertNext(i -> Assertions.assertEquals("Trump", i.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    public void update(){
        var userDto = Mono.just(new UserDto(null, "Donald Trump", "makeAmericaGreatAgain@gmail.com", Role.ROLE_ADMIN));
        this.userClient.update(8, userDto)
                .doOnNext(i -> log.info("получено: {}", userDto))
                .as(StepVerifier::create)
                .assertNext(i -> Assertions.assertEquals("Donald Trump", i.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    public void delete(){
        this.userClient.delete(4)
                .then(Mono.defer(() -> this.userClient.getById(4)))
                .as(StepVerifier::create)
                .expectError()
                .verify();
    }
}
