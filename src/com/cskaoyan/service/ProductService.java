package com.cskaoyan.service;

import com.cskaoyan.bean.Product;
import com.cskaoyan.utils.Page;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    boolean addProduct(Product product) throws SQLException;

    Page<Product> findPage(String num) throws SQLException;

    boolean deleteOne(String pid) throws SQLException;

    Product findProductByPid(String pid) throws SQLException;

    boolean updateProduct(Product product) throws SQLException;

    Page<Product> findProductByMultiCondition(String pid, String pname, String cid, String minprice, String maxprice, String num) throws SQLException;

    List<Product> findTopProducts() throws SQLException;

    List<Product> findHotProducts() throws SQLException;

    List<Product> findProductByCid(String cid) throws SQLException;

    List<Product> findPartProductByKeyword(String keyword) throws SQLException;

    Page<Product> findPartHotProducts(String num) throws SQLException;

    Page<Product> findPartProductByCid(String cid, String num) throws SQLException;

    Page<Product> findPartProductByKeyword(String num, String keyword) throws SQLException;
}
