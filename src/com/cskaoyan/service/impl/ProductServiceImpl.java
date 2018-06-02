package com.cskaoyan.service.impl;

import com.cskaoyan.bean.Product;
import com.cskaoyan.dao.ProductDao;
import com.cskaoyan.dao.impl.ProductDaoImpl;
import com.cskaoyan.service.ProductService;
import com.cskaoyan.utils.Page;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    ProductDao productDao = new ProductDaoImpl();

    @Override
    public boolean addProduct(Product product) throws SQLException {
        return productDao.addProduct(product);
    }

    @Override
    public Page<Product> findPage(String num) throws SQLException                                                                                                                                                                                                                                              {

        int currentPage = Integer.parseInt(num);
        int totalRecordsNum = productDao.getTotalRecordsNum();
        int perPageNum = Page.PRODUCT_PER_PAGE_NUM;
        Page<Product> productPage = new Page<Product>(currentPage, totalRecordsNum, perPageNum);
        List<Product> partProducts = productDao.getPartProduct(perPageNum, (currentPage - 1) * perPageNum);
        productPage.setRecords(partProducts);
        return productPage;
    }

    @Override
    public boolean deleteOne(String pid) throws SQLException {
        return productDao.deleteOne(pid);
    }

    @Override
    public Product findProductByPid(String pid) throws SQLException {
        return productDao.findProductByPid(pid);
    }

    @Override
    public boolean updateProduct(Product product) throws SQLException {
        return productDao.updateProduct(product);
    }

    @Override
    public Page<Product> findProductByMultiCondition(String pid, String pname, String cid, String minprice, String maxprice, String num) throws SQLException {
        int currentPage = Integer.parseInt(num);
        int totalRecordsNum = productDao.getTotalRecordsNumByMultiCondition(pid, pname, cid, minprice, maxprice);
        int perPageNum = Page.PRODUCT_PER_PAGE_NUM;
        Page<Product> page = new Page<Product>(currentPage, totalRecordsNum, perPageNum);
        List<Product> partProducts = productDao.getProductByMultiCondition(pid, pname, cid, minprice, maxprice,
                perPageNum, (currentPage - 1) * perPageNum);
        page.setRecords(partProducts);
        ///////
        System.out.println(page);
        ///////
        return page;
    }

    @Override
    public List<Product> findTopProducts() throws SQLException {
        //假设top商品为价格最贵的三个商品
        return productDao.getTopProduct();

    }

    @Override
    public List<Product> findHotProducts() throws SQLException {
        return productDao.findHotProducts();
    }

    @Override
    public List<Product> findProductByCid(String cid) throws SQLException {
        return productDao.findProductByCid(cid);
    }

    @Override
    public List<Product> findPartProductByKeyword(String keyword) throws SQLException {
        return productDao.findProductByName(keyword);
    }

    @Override
    public Page<Product> findPartHotProducts(String num) throws SQLException {

        int currentPage = Integer.parseInt(num);
        int totalRecordsNum = Page.HOTPRODUCT_TOTAL_NUM;
        int perPageNum = Page.HOTPRODUCT_PER_PAGE_NUM;
        Page<Product> hotProductPage = new Page<Product>(currentPage, totalRecordsNum, perPageNum);

        //由于查询的条件为库存最少的10条记录，此时sql语句中要用到limit
        //而分页查询时也用到了limit，发生冲突
        //故应该在service层对limit加一个逻辑判断，以确保查询结果为正确结果。
        int offset = (currentPage - 1) * perPageNum;
        int limit = totalRecordsNum - offset > perPageNum ? perPageNum : totalRecordsNum - offset;
        List<Product> partHotProducts = productDao.getPartHotProduct(limit, offset);
        hotProductPage.setRecords(partHotProducts);
        return hotProductPage;
    }

    @Override
    public Page<Product> findPartProductByCid(String cid, String num) throws SQLException {
        int currentPage = Integer.parseInt(num);
        int totalRecordsNum = productDao.getTotalProductByCidNum(cid);
        int perPageNum = Page.PRODUCT_PER_PAGE_NUM;
        Page<Product> productPage = new Page<Product>(currentPage, totalRecordsNum, perPageNum);
        List<Product> partProduct = productDao.getPartProductByCid(cid, perPageNum, (currentPage - 1) * perPageNum);
        productPage.setRecords(partProduct);
        return productPage;
    }

    @Override
    public Page<Product> findPartProductByKeyword(String num, String keyword) throws SQLException {

        int currentPage = Integer.parseInt(num);
        int totalRecordsNum = productDao.getTotalProductByKeywordNum(keyword);
        int perPageNum = Page.PRODUCT_PER_PAGE_NUM;
        Page<Product> productPage = new Page<Product>(currentPage, totalRecordsNum, perPageNum);
        List<Product> partProduct = productDao.getPartProductByKeyword(keyword, perPageNum, (currentPage - 1) * perPageNum);
        productPage.setRecords(partProduct);
        return productPage;

    }
}
