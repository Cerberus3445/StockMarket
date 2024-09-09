package com.cerberus.demotradingweb.security;

import com.cerberus.demotradingweb.client.UserClient;
import com.cerberus.demotradingweb.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService {

    private final UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto person = this.userClient.getByUsernameOrEmail(username);

        if (person == null) throw new UsernameNotFoundException("Пользователь не найден");

        return new PersonDetails(person);
    }
}