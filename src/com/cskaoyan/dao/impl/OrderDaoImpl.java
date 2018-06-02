package com.cskaoyan.dao.impl;

import com.cskaoyan.bean.Order;
import com.cskaoyan.bean.OrderItem;
import com.cskaoyan.dao.OrderDao;
import com.cskaoyan.utils.MyC3P0DataSource;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    QueryRunner queryRunner = new QueryRunner(MyC3P0DataSource.getDataSource());

    @Override
    public boolean createOrder(Order order) throws SQLException {

        int update = queryRunner.update("insert into `order` values (?, ?, ?, ?, ?, 1, ?, ?)",
                order.getOid(),
                order.getMoney(),
                order.getRecipients(),
                order.getTel(),
                order.getAddress(),
                new Date(),
                order.getUid());

        return update == 1;
    }

    @Override
    public void createOrderItem(OrderItem orderItem) throws SQLException {
        int update = queryRunner.update("insert into orderitem values (null, ?, ?, ?)",
                orderItem.getOid(),
                orderItem.getPid(),
                orderItem.getBuynum());
        if (update != 1){
            throw new IllegalArgumentException("创建订单项失败");
        }
    }

    @Override
    public List<Order> findOrdersByUid(int uid) throws SQLException {

        List<Order> orders = queryRunner.query("select * from `order` where uid = ?",
                new BeanListHandler<Order>(Order.class),
                uid);
        return orders;
    }

    @Override
    public boolean updateOrderState(String oid, String state) throws SQLException {

        int update = queryRunner.update("update `order` set state = ? where oid = ?",
                state,
                oid);

        return update == 1;
    }
}
