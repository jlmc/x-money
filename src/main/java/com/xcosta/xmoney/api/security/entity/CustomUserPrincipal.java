package com.xcosta.xmoney.api.security.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public class CustomUserPrincipal extends org.springframework.security.core.userdetails.User {

    private final User user;

    private CustomUserPrincipal(User user, Set<SimpleGrantedAuthority> grantedAuthorities) {
        super(user.getUsername(), user.getPassword(), grantedAuthorities);
        this.user = user;
    }

    public static CustomUserPrincipal of(User user) {
        Set<SimpleGrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(Role::getName)
                .map(String::toUpperCase)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new CustomUserPrincipal(user, grantedAuthorities);
    }

    public User getUser() {
        return user;
    }
}
