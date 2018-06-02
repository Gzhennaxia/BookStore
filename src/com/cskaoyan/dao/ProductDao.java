package com.cskaoyan.dao;

import com.cskaoyan.bean.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    boolean addProduct(Product product) throws SQLException;

    int getTotalRecordsNum() throws SQLException;

    List<Product> getPartProduct(int limit, int offset) throws SQLException;

    boolean deleteOne(String pid) throws SQLException;

    Product findProductByPid(String pid) throws SQLException;

    boolean updateProduct(Product product) throws SQLException;

    List<Product> getProductByMultiCondition(String pid, String pname, String cid, String minPrice, String maxPrice, int limit, int offset) throws SQLException;

    int getTotalRecordsNumByMultiCondition(String pid, String pname, String cid, String minprice, String maxprice) throws SQLException;

    List<Product> getTopProduct() throws SQLException;

    List<Product> findHotProducts() throws SQLException;

    List<Product> findProductByCid(String cid) throws SQLException;

    List<Product> findProductByName(String pname) throws SQLException;

    List<Product> getPartHotProduct(int limit, int offset) throws SQLException;

    int getTotalProductByCidNum(String num) throws SQLException;

    List<Product> getPartProductByCid(String cid, int limit, int offset) throws SQLException;

    int getTotalProductByKeywordNum(String keyword) throws SQLException;

    List<Product> getPartProductByKeyword(String keyword, int limit, int offset) throws SQLException;
}
