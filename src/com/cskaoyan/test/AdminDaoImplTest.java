package com.cskaoyan.test;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.dao.AdminDao;
import com.cskaoyan.dao.impl.AdminDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class AdminDaoImplTest {

    AdminDao adminDao = new AdminDaoImpl();

    @Before
    public void setUp() throws Exception {
        adminDao = new AdminDaoImpl();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addAdmin() throws SQLException {
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("666666");
        boolean ret = adminDao.addAdmin(admin);
        System.out.println("ret = " + ret);
    }

    @Test
    public void isRegistered() throws SQLException {
        Admin admin = new Admin();
        admin.setUsername("admin");
        boolean ret = adminDao.isRegistered(admin);
        System.out.println("ret = " + ret);
    }

    @Test
    public void findPortAdmin() throws SQLException {
        List<Admin> portAdmin = adminDao.findPortAdmin(2, 0);
        System.out.println(portAdmin);
    }

    @Test
    public void updateAdmin() throws SQLException {
        Admin admin = new Admin();
        admin.setUsername("admin02");
        admin.setPassword("1234");
        boolean ret = adminDao.updateAdmin(admin);
        System.out.println("ret = " + ret);
    }
}