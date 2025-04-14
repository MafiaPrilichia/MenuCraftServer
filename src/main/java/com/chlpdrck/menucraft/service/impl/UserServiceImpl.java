package com.chlpdrck.menucraft.service.impl;

import com.chlpdrck.menucraft.entity.Role;
import com.chlpdrck.menucraft.entity.User;
import com.chlpdrck.menucraft.repository.UserRepository;
import com.chlpdrck.menucraft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    @Override
    public boolean checkUserAdmin(String username) {
        User user = getUserByUsername(username);

        return user.getRole().equals(Role.ADMIN);
    }
}