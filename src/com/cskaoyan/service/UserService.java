package com.cskaoyan.service;

import com.cskaoyan.bean.User;

import java.sql.SQLException;

public interface UserService {
    boolean isValidUsername(String username) throws SQLException;

    boolean register(User user) throws SQLException;

    boolean activeUser(String activecode) throws SQLException;

//    int verifyUser(String username, String password) throws SQLException;

    User findUser(String username, String password) throws SQLException;

    boolean updateUser(User user) throws SQLException;
}
