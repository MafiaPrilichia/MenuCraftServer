package com.chlpdrck.menucraft.service;

import com.chlpdrck.menucraft.entity.User;

public interface UserService {

    User getUserByUsername(String username);

    boolean checkUserAdmin(String username);
}
