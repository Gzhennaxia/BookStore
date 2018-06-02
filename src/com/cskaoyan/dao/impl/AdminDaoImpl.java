package com.cskaoyan.dao.impl;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.dao.AdminDao;
import com.cskaoyan.utils.MyC3P0DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.management.Query;
import java.sql.SQLException;
import java.util.List;

public class AdminDaoImpl implements AdminDao {

    QueryRunner queryRunner;

    public AdminDaoImpl() {
        queryRunner = new QueryRunner(MyC3P0DataSource.getDataSource());
    }

    @Override
    public boolean addAdmin(Admin admin) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSource.getDataSource());
        int update = queryRunner.update("insert into admin values(null, ?, ?)", admin.getUsername(), admin.getPassword());
        return update==1;
    }

    @Override
    public boolean isRegistered(Admin admin) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSource.getDataSource());
        Admin query = queryRunner.query("select * from admin where username = ?", new BeanHandler<Admin>(Admin.class), admin.getUsername());
        return query != null;
    }

    @Override
    public int getTotalRecordNumber() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSource.getDataSource());
        Long query = (Long) queryRunner.query("select count(*) from admin", new ScalarHandler());
        return query.intValue();
    }

    @Override
    public List<Admin> findPortAdmin(int limit, int offset) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSource.getDataSource());
        List<Admin> query = queryRunner.query("select * from admin limit ? offset ?",
                new BeanListHandler<Admin>(Admin.class),
                limit,
                offset);
        return query;
    }

    @Override
    public boolean updateAdmin(Admin admin) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSource.getDataSource());
        int update = queryRunner.update("update admin set password = ? where username = ?",
                admin.getPassword(),
                admin.getUsername());
        return update == 1;
    }

    @Override
    public boolean isExistAdmin(Admin admin) throws SQLException {
        Admin query = queryRunner.query("select * from admin where username = ? and password = ?",
                new BeanHandler<Admin>(Admin.class),
                admin.getUsername(),
                admin.getPassword());
        return query != null;
    }
}
