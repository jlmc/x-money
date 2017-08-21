package com.xcosta.xmoney.api.security.control;

import com.xcosta.xmoney.api.security.entity.Role;
import com.xcosta.xmoney.api.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        UserDetails u = user.map(this::toUserDetails).orElseThrow(() -> new UsernameNotFoundException("username or password invalid"));

        return u;
    }

    private UserDetails toUserDetails(User user) {

        Set<SimpleGrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(Role::getName)
                .map(String::toUpperCase)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
