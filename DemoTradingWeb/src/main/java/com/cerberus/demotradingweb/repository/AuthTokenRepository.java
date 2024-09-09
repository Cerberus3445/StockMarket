package com.cerberus.demotradingweb.repository;

import com.cerberus.demotradingweb.model.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Integer> {

}
