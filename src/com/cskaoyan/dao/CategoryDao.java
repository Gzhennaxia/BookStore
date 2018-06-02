package com.cskaoyan.dao;

import com.cskaoyan.bean.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {

    boolean addCategory(String cname) throws SQLException;

    List<Category> findAllCategory() throws SQLException;

    boolean updateCategory(Category category) throws SQLException;

    boolean deleteOne(String cid) throws SQLException;

    int findTotalNumber() throws SQLException;

    List<Category> findPartCategory(int limit, int offset) throws SQLException;
}
