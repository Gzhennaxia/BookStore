package com.cskaoyan.dao;

import com.cskaoyan.bean.ShoppingCart;
import com.cskaoyan.bean.ShoppingItem;

import java.sql.SQLException;
import java.util.List;

public interface CartDao {
    boolean addCart(String pid, String uid, String snum) throws SQLException;

    ShoppingCart findCartByUid(String uid) throws SQLException;

    List<ShoppingItem> findShoppingItemsBySid(int sid) throws SQLException;

//    int findSnum(String pid, String uid) throws SQLException;
//
//    int findSnum(int sid, int pid) throws SQLException;

    ShoppingItem findShoppingItem(int sid, String pid) throws SQLException;

    boolean updateSnum(ShoppingItem shoppingItem) throws SQLException;

    boolean delItem(String uid, String itemid) throws SQLException;

    boolean delItemByItemId(String itemid) throws SQLException;
}
