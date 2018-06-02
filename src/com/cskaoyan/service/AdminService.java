package com.cskaoyan.service;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.utils.Page;

import java.sql.SQLException;

public interface AdminService {
    boolean addAdmin(Admin admin) throws SQLException;

    boolean isRegistered(Admin admin) throws SQLException;

    Page<Admin> findPage(String num) throws SQLException;

    boolean updateAdmin(Admin admin) throws SQLException;

    boolean isExistAdmin(Admin admin) throws SQLException;
}
