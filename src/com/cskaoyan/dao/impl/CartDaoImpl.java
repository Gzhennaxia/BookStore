package com.cskaoyan.dao.impl;

import com.cskaoyan.bean.ShoppingCart;
import com.cskaoyan.bean.ShoppingItem;
import com.cskaoyan.bean.User;
import com.cskaoyan.dao.CartDao;
import com.cskaoyan.utils.MyC3P0DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CartDaoImpl implements CartDao {

    static QueryRunner queryRunner;

    static {
        queryRunner = new QueryRunner(MyC3P0DataSource.getDataSource());
    }

    @Override
    public boolean addCart(String pid, String uid, String snum) throws SQLException {
        //根据uid得到购物车
        ShoppingCart shoppingCart = findCartByUid("" + uid);

        //根据sid和pid添加购物项
        int ret = queryRunner.update("insert into shoppingitem values (null, ?, ?, ?)",
                shoppingCart.getSid(),
                pid,
                snum);

        return ret == 1;
    }

    @Override
    public ShoppingCart findCartByUid(String uid) throws SQLException {

        ShoppingCart shoppingCart = queryRunner.query("select * from shoppingcar where uid = ?",
                new BeanHandler<>(ShoppingCart.class),
                uid);

        return shoppingCart;
    }

    @Override
    public List<ShoppingItem> findShoppingItemsBySid(int sid) throws SQLException {

        List<ShoppingItem> shoppingItems = queryRunner.query("select * from shoppingitem where sid = ?",
                new BeanListHandler<ShoppingItem>(ShoppingItem.class),
                sid);

        return shoppingItems;
    }

    @Override
    public ShoppingItem findShoppingItem(int sid, String pid) throws SQLException {

        ShoppingItem shoppingItem = queryRunner.query("select * from shoppingitem where sid = ? and pid = ?",
                new BeanHandler<>(ShoppingItem.class),
                sid,
                pid);

        return shoppingItem;
    }

    @Override
    public boolean updateSnum(ShoppingItem shoppingItem) throws SQLException {

        int ret = queryRunner.update("update shoppingitem set snum = ? where itemid = ?",
                shoppingItem.getSnum(),
                shoppingItem.getItemid());

        return ret == 1;
    }

    @Override
    public boolean delItem(String uid, String itemid) throws SQLException {
        int update = queryRunner.update("delete from shoppingitem where uid = ? and itemid = ?",
                uid,
                itemid);
        return update == 1;
    }

    @Override
    public boolean delItemByItemId(String itemid) throws SQLException {
        int update = queryRunner.update("delete from shoppingitem where itemid = ?",
                itemid);
        return update == 1;
    }

//    @Override
//    public int findSnum(String pid, String uid) throws SQLException {
//
//        //1、通过uid找到sid
//        ShoppingCart shoppingCart = queryRunner.query("select * from shoppingcar where uid = ?",
//                new BeanHandler<>(ShoppingCart.class),
//                uid);
//        int sid = shoppingCart.getUid();
//
//        //2、通过sid和pid找到snum
//        ShoppingItem findShoppingItem = queryRunner.query("select * from shoppingitem where sid = ? and pid = ?",
//                new BeanHandler<>(ShoppingItem.class),
//                sid,
//                pid);
//
//        int snum = 0;
//        if (findShoppingItem != null) {
//            snum = findShoppingItem.getSnum();
//        }
//
//        //3、返回snum
//        return snum;
//    }
//
//    @Override
//    public int findSnum(int sid, int pid) throws SQLException {
//        ShoppingItem findShoppingItem = queryRunner.query("select * from shoppingitem where sid = ? and pid = ?",
//                new BeanHandler<>(ShoppingItem.class),
//                sid,
//                pid);
//
//        int snum = 0;
//        if (findShoppingItem != null) {
//            snum = findShoppingItem.getSnum();
//        }
//
//        //3、返回snum
//        return snum;
//    }

    public static void createCartForUser(User user) throws SQLException {

        queryRunner.update("insert into shoppingcar value (null, ?)", user.getUid());

    }
}
