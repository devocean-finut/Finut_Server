package com.finut.finut_server.service;

import com.finut.finut_server.domain.user.Users;
import com.finut.finut_server.domain.user.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;


    public void saveRefreshToken(String email, String refreshToken) {
        Optional<Users> optionalUser = usersRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            user.setRefreshToken(refreshToken);
            usersRepository.save(user);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}

