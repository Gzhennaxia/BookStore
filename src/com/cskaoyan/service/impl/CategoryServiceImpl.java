package com.cskaoyan.service.impl;

import com.cskaoyan.bean.Category;
import com.cskaoyan.dao.CategoryDao;
import com.cskaoyan.dao.impl.CategoryDaoImpl;
import com.cskaoyan.service.CategoryService;
import com.cskaoyan.utils.Page;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public boolean addCategory(String cname) throws SQLException {
        return categoryDao.addCategory(cname);
    }

    @Override
    public List<Category> findAllCategory() throws SQLException {
        return categoryDao.findAllCategory();
    }

    @Override
    public boolean updateCategory(Category category) throws SQLException {
        return categoryDao.updateCategory(category);
    }

    @Override
    public boolean deleteOne(String cid) throws SQLException {
        return categoryDao.deleteOne(cid);
    }

    @Override
    public Page<Category> findPage(String num) throws SQLException {

        //分类页面每页显示4条记录
        int perPageNum = Page.CATEGORY_PER_PAGE_NUM;

        //当前位于第几页
        int currentPageNum = Integer.parseInt(num);

        //总记录数
        int totalRecordsNum = categoryDao.findTotalNumber();

        Page<Category> categoryPage = new Page<Category>(currentPageNum, totalRecordsNum, perPageNum);

        //当前页的数据集合
        //注意MySql第一个被检索的行是第0行，而不是第1行，故传值时要注意调整
        List<Category> records = categoryDao.findPartCategory(perPageNum, (currentPageNum - 1) * perPageNum);
        categoryPage.setRecords(records);

        return categoryPage;
    }
}
