package com.cskaoyan.service.impl;

import com.cskaoyan.bean.Product;
import com.cskaoyan.bean.ShoppingCart;
import com.cskaoyan.bean.ShoppingItem;
import com.cskaoyan.dao.CartDao;
import com.cskaoyan.dao.ProductDao;
import com.cskaoyan.dao.impl.CartDaoImpl;
import com.cskaoyan.dao.impl.ProductDaoImpl;
import com.cskaoyan.service.CartService;

import java.sql.SQLException;
import java.util.List;

public class CartServiceImpl implements CartService {

    CartDao cartDao = new CartDaoImpl();

    @Override
    public boolean addCart(String pid, String uid, String snum) throws SQLException {
//        //1、判断该用户的购物车中是否有该商品，有的话数量加1，没有则添加到购物车
//        int snum = cartDao.findSnum(pid, uid);
//        if (0 == snum){
//            //用户购物车中以前没有该商品
//            boolean ret = cartDao.addCart(pid, uid);
//        }else {
//            //用户购物车中以前有该商品
//            snum += 1;
//            boolean ret = cartDao.addNum(pid, uid, snum);
//        }
//
//        return cartDao.addCart(pid, uid);

        //1、获取该用户的购物车
        ShoppingCart shoppingCart = cartDao.findCartByUid(uid);

        //2、根据sid和pid找到shoppingItem
        int sid = shoppingCart.getSid();
        ShoppingItem shoppingItem = cartDao.findShoppingItem(sid, pid);

        //3、如果shoppingItem等于null，则将该商品添加到购物车，否则将snum加1在更新回购物车
        boolean ret;
        if (shoppingItem == null){
            ret = cartDao.addCart(pid, uid, snum);
        }else {
            shoppingItem.setSnum(shoppingItem.getSnum() + Integer.parseInt(snum));
            ret = cartDao.updateSnum(shoppingItem);
        }

        return ret;
    }

    @Override
    public ShoppingCart findCartByUid(int uid) throws SQLException {

        //1、查找该用户对应的购物车
        ShoppingCart shoppingCart = cartDao.findCartByUid("" + uid);

        //2、查找该购物车所对应的购物项列表
        int sid = shoppingCart.getSid();
        List<ShoppingItem> shoppingItems = cartDao.findShoppingItemsBySid(sid);

        //3、为每条购物项填充商品信息
        ProductDao productDao = new ProductDaoImpl();
        for (ShoppingItem item : shoppingItems) {
            Product productByPid = productDao.findProductByPid(item.getPid());
            item.setProduct(productByPid);
        }

        //4、将购物项列表填充到购物车中
        shoppingCart.setShoppingItems(shoppingItems);

        //5、返回购物车
        return shoppingCart;
    }

    @Override
    public boolean delItem(String uid, String itemid) throws SQLException {
        return cartDao.delItem(uid, itemid);
    }

    @Override
    public boolean delItemByItemId(String itemid) throws SQLException {
        return cartDao.delItemByItemId(itemid);
    }

    @Override
    public ShoppingItem findShoppingItem(int sid, String pid) throws SQLException {
        return cartDao.findShoppingItem(sid,pid);
    }
}
