package com.finut.finut_server.service;

import com.finut.finut_server.domain.level.Level;
import com.finut.finut_server.domain.level.LevelRepository;
import com.finut.finut_server.domain.user.Users;
import com.finut.finut_server.domain.user.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LevelService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private LevelRepository levelRepository;
    @Transactional
    public Users upgradeUserLevel(Long userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        Long currentLevelId = user.getLevel().getId();
        Optional<Level> nextLevelOpt = levelRepository.findById(currentLevelId + 1);

        if (nextLevelOpt.isPresent()) {
            user.setLevel(nextLevelOpt.get());
            usersRepository.save(user);
            return user;
        } else {
            throw new IllegalStateException("No higher level available. User is already at the highest level.");
        }
    }

}
