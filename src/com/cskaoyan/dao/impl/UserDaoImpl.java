package com.cskaoyan.dao.impl;

import com.cskaoyan.bean.User;
import com.cskaoyan.dao.UserDao;
import com.cskaoyan.utils.MyC3P0DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    QueryRunner queryRunner = new QueryRunner(MyC3P0DataSource.getDataSource());

    @Override
    public boolean isValidUsername(String username) throws SQLException {

        User query = queryRunner.query("select * from user where username = ?",
                new BeanHandler<>(User.class),
                username);
        return query != null;
    }

    @Override
    public boolean saveUser(User user) throws SQLException {

        int update = queryRunner.update("insert into user values(null, ?, ?, ?, ?, ?, ?, 0, ?)",
                user.getUsername(),
                user.getNickname(),
                user.getPassword(),
                user.getEmail(),
                user.getBirthday(),
                user.getUpdatetime(),
                user.getActivecode());

        return update == 1;
    }

    @Override
    public boolean activeUser(String activecode) throws SQLException {

        int update = queryRunner.update("update user set state = 1 where activecode = ?", activecode);

        return update == 1;
    }

    @Override
    public User findUser(String username, String password) throws SQLException {

        User query = queryRunner.query("select * from user where username = ? and password = ?",
                new BeanHandler<>(User.class),
                username,
                password);
        return query;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {

        int update = queryRunner.update("update user set nickname = ?, password = ?, email = ?, birthday = ?, updatetime = ? where uid = ?",
                user.getNickname(),
                user.getPassword(),
                user.getEmail(),
                user.getBirthday(),
                user.getUpdatetime(),
                user.getUid());

        return update == 1;
    }

//    @Override
//    public boolean verifyUser(String username, String password) throws SQLException {
//
//        User query = queryRunner.query("select * from user where username = ? and password = ?",
//                new BeanHandler<>(User.class),
//                username,
//                password);
//        return query != null;
//    }
}
