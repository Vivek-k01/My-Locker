package com.example.demo.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyAppUserService implements UserDetailsService {
    
    @Autowired
    private MyAppUserRepository repository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyAppUser user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        // Seedha return - koi verified check nahi, koi role nahi
        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }
}
