package com.cskaoyan.dao;

import com.cskaoyan.bean.User;

import java.sql.SQLException;

public interface UserDao {
    boolean isValidUsername(String username) throws SQLException;

    boolean saveUser(User user) throws SQLException;

    boolean activeUser(String activecode) throws SQLException;

//    boolean verifyUser(String username, String password) throws SQLException;

    User findUser(String username, String password) throws SQLException;

    boolean updateUser(User user) throws SQLException;
}
