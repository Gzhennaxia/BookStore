package com.cskaoyan.service.impl;

import com.cskaoyan.bean.User;
import com.cskaoyan.dao.UserDao;
import com.cskaoyan.dao.impl.CartDaoImpl;
import com.cskaoyan.dao.impl.UserDaoImpl;
import com.cskaoyan.service.UserService;
import com.cskaoyan.utils.SendJMail;

import java.sql.SQLException;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    UserDao userDao = new UserDaoImpl();

    @Override
    public boolean isValidUsername(String username) throws SQLException {
        return userDao.isValidUsername(username);
    }

    @Override
    public boolean register(User user) throws SQLException {

        if (user == null) {
            throw new IllegalArgumentException("用户为空");
        }

        //给用户邮件发激活邮件
        //用户点击激活连接后进行激活
        String activeCode = UUID.randomUUID().toString();
        String content = "<a href='http://192.168.3.106/user/UserServlet?op=activeUser&activecode=" + activeCode + "'>点击激活账户</a>";
        SendJMail.sendMail(user.getEmail(), content);

        user.setActivecode(activeCode);

        //保存用户信息
        boolean ret = userDao.saveUser(user);

        //为该用户创建一个购物车
        User user1 = userDao.findUser(user.getUsername(), user.getPassword());
        CartDaoImpl.createCartForUser(user1);

        return ret;
    }

    @Override
    public boolean activeUser(String activecode) throws SQLException {

        return userDao.activeUser(activecode);

    }

    @Override
    public User findUser(String username, String password) throws SQLException {
        return userDao.findUser(username, password);
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        return userDao.updateUser(user);
    }

//    @Override
//    public int verifyUser(String username, String password) throws SQLException {
//
//        //首先查找该用户是否注册过
//        boolean isRegistered = userDao.findUser(username, password);
//        if (!isRegistered){
//            return 2;
//        }
//        //再看是否激活
//        boolean isActived = userDao.findState(username, password);
//        if (isActived){
//            return 1;
//        }else {
//            return 3;
//        }
//    }
}
