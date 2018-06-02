package com.cskaoyan.test;

import com.cskaoyan.bean.Product;
import com.cskaoyan.dao.ProductDao;
import com.cskaoyan.dao.impl.ProductDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class ProductDaoImplTest {

    ProductDao productDao;

    @Before
    public void setUp() throws Exception {
        productDao = new ProductDaoImpl();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addProduct() {
    }

    @Test
    public void getTotalRecordsNum() {
    }

    @Test
    public void getPartProduct() throws SQLException {
        List<Product> partProduct = productDao.getPartProduct(3, 0);
        System.out.println(partProduct);
    }

    @Test
    public void deleteOne() throws SQLException {
        boolean ret = productDao.deleteOne("123123");
        System.out.println("ret = " + ret);
    }

    @Test
    public void findProductByPid() throws SQLException {
        Product productByPid = productDao.findProductByPid("111");
        System.out.println(productByPid);
    }
}