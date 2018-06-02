package com.cskaoyan.dao.impl;

import com.cskaoyan.bean.Category;
import com.cskaoyan.dao.CategoryDao;
import com.cskaoyan.utils.MyC3P0DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {


    @Override
    public boolean addCategory(String cname) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSource.getDataSource());
        int update = queryRunner.update("insert into category values(null, ?);", cname);
        return update==1;
    }

    @Override
    public List<Category> findAllCategory() throws SQLException {

        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSource.getDataSource());
        List<Category> categoryList = queryRunner.query("select * from category;", new BeanListHandler<Category>(Category.class));
        return categoryList;
    }

    @Override
    public boolean updateCategory(Category category) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSource.getDataSource());
        int update = queryRunner.update("update category set cname = ? where cid = ?", category.getCname(), category.getCid());
        return update==1;
    }

    @Override
    public boolean deleteOne(String cid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSource.getDataSource());
        int update = queryRunner.update("delete from category where cid = ?", cid);
        return update==1;
    }

    @Override
    public int findTotalNumber() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSource.getDataSource());
        Long query = (Long)queryRunner.query("select count(*) from category",
                new ScalarHandler());
        return query.intValue();
    }

    @Override
    public List<Category> findPartCategory(int limit, int offset) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSource.getDataSource());

        // LIMIT 4 OFFSET 5 指示查询从第6行起的4行数据。第一个数字是指检索的行数，第二个数字是从哪儿开始。
        List<Category> query = queryRunner.query("select * from category limit ? offset ?",
                new BeanListHandler<Category>(Category.class), limit, offset);
        return query;
    }
}
