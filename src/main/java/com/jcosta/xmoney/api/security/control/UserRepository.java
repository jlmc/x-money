package com.jcosta.xmoney.api.security.control;

import com.jcosta.xmoney.api.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u from User u left join fetch u.roles where trim(lower(u.username)) = trim(lower(:username))")
    Optional<User> findByUsername(@Param("username") String username);
}
