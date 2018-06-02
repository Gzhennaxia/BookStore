package com.cskaoyan.service;

import com.cskaoyan.bean.Category;
import com.cskaoyan.utils.Page;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {

    boolean addCategory(String cname) throws SQLException;

    List<Category> findAllCategory() throws SQLException;

    boolean updateCategory(Category category) throws SQLException;

    boolean deleteOne(String cid) throws SQLException;

    Page<Category> findPage(String num) throws SQLException;
}
