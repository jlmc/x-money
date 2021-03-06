package com.jcosta.xmoney.api.security.control;

import com.jcosta.xmoney.api.security.entity.CustomUserPrincipal;
import com.jcosta.xmoney.api.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(this::toUserDetails).orElseThrow(() -> new UsernameNotFoundException("username or password invalid"));
    }

    private UserDetails toUserDetails(User user) {
        /*
        Set<SimpleGrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(Role::getName)
                .map(String::toUpperCase)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
        */
        return CustomUserPrincipal.of(user);
    }
}
