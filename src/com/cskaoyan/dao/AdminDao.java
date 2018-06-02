package com.cskaoyan.dao;

import com.cskaoyan.bean.Admin;

import java.sql.SQLException;
import java.util.List;

public interface AdminDao {

    boolean addAdmin(Admin admin) throws SQLException;

    boolean isRegistered(Admin admin) throws SQLException;

    int getTotalRecordNumber() throws SQLException;

    List<Admin> findPortAdmin(int limit, int offset) throws SQLException;

    boolean updateAdmin(Admin admin) throws SQLException;

    boolean isExistAdmin(Admin admin) throws SQLException;
}
