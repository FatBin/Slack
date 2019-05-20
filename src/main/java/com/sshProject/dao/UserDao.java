package com.sshProject.dao;

import com.sshProject.entity.User;

public interface UserDao {
    boolean addUser(User user);

    User findUserByemail(String email);

    User findUserById(int userid);

    int saveUser(User user);
}
