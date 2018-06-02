package com.cskaoyan.test;

import com.cskaoyan.bean.Category;
import com.cskaoyan.dao.CategoryDao;
import com.cskaoyan.dao.impl.CategoryDaoImpl;
import com.cskaoyan.utils.MyC3P0DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class CategoryDaoImplTest {

    CategoryDao categoryDao;

    @org.junit.Before
    public void setUp() throws Exception {
        categoryDao = new CategoryDaoImpl();
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @org.junit.Test
    public void addCategory() throws SQLException {
        String s = "短篇小说";
        boolean ret = categoryDao.addCategory(s);
        System.out.println("ret = " + ret);
    }

    @org.junit.Test
    public void findAllCategory() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSource.getDataSource());
        List<Category> categoryList = queryRunner.query("select * from category;", new BeanListHandler<Category>(Category.class));
        System.out.println(categoryList);
    }

    @org.junit.Test
    public void updateCategory() throws SQLException {
        Category category = new Category();
        category.setCid("1");
        category.setCname("青春文学");
        boolean ret = categoryDao.updateCategory(category);
        System.out.println(ret);

    }

}