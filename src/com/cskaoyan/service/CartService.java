package com.cskaoyan.service;

import com.cskaoyan.bean.ShoppingCart;
import com.cskaoyan.bean.ShoppingItem;

import java.sql.SQLException;

public interface CartService {
    boolean addCart(String pid, String uid, String snum) throws SQLException;

    ShoppingCart findCartByUid(int uid) throws SQLException;

    boolean delItem(String uid, String itemid) throws SQLException;

    boolean delItemByItemId(String itemid) throws SQLException;

    ShoppingItem findShoppingItem(int sid, String pid) throws SQLException;
}
