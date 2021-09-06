package com.example.patient.security;

import com.example.patient.fr.entities.UserEntity;
import com.example.patient.fr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);
        System.out.println(username);
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException("No user named " + username);
        } else {
            return new UserDetailsImpl(user);
        }
    }
}
