package com.chlpdrck.menucraft.service;

import com.chlpdrck.menucraft.entity.User;

public interface UserService {

    User getUserByUsername(String username);
    User getUserById(Long id);
    boolean checkUserAdmin(String username);
}
