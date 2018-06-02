package com.cskaoyan.service.impl;

import com.cskaoyan.bean.Order;
import com.cskaoyan.bean.OrderItem;
import com.cskaoyan.dao.OrderDao;
import com.cskaoyan.dao.impl.OrderDaoImpl;
import com.cskaoyan.service.OrderService;

import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    OrderDao orderDao = new OrderDaoImpl();

    @Override
    public boolean createOrder(Order order) throws SQLException {
        return orderDao.createOrder(order);
    }

    @Override
    public void createOrderItem(OrderItem orderItem) throws SQLException {
        orderDao.createOrderItem(orderItem);
    }

    @Override
    public List<Order> findOrdersByUid(int uid) throws SQLException {
        return orderDao.findOrdersByUid(uid);
    }

    @Override
    public boolean updateOrderState(String oid, String state) throws SQLException {
        return orderDao.updateOrderState(oid, state);
    }
}
