package com.cskaoyan.service.impl;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.bean.Category;
import com.cskaoyan.dao.AdminDao;
import com.cskaoyan.dao.impl.AdminDaoImpl;
import com.cskaoyan.service.AdminService;
import com.cskaoyan.utils.Page;

import java.sql.SQLException;
import java.util.List;

public class AdminServiceImpl implements AdminService {

    AdminDao adminDao = new AdminDaoImpl();

    @Override
    public boolean addAdmin(Admin admin) throws SQLException {
        return adminDao.addAdmin(admin);
    }

    @Override
    public boolean isRegistered(Admin admin) throws SQLException {
        return adminDao.isRegistered(admin);
    }

    @Override
    public Page<Admin> findPage(String num) throws SQLException {
        int currentPageNum = Integer.parseInt(num);
        int totalRecordsNum = adminDao.getTotalRecordNumber();
        int perPageNum = Page.ADMIN_PER_PAGE_NUM;
        Page<Admin> page = new Page<>(currentPageNum, totalRecordsNum, perPageNum);

        //当前页的数据集合
        //注意MySql第一个被检索的行是第0行，而不是第1行，故传值时要注意调整
        List<Admin> records = adminDao.findPortAdmin(perPageNum, (currentPageNum - 1) * perPageNum);
        page.setRecords(records);
        return page;
    }

    @Override
    public boolean updateAdmin(Admin admin) throws SQLException {

        return adminDao.updateAdmin(admin);
    }

    @Override
    public boolean isExistAdmin(Admin admin) throws SQLException {
        return adminDao.isExistAdmin(admin);
    }
}
