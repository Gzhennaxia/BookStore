package com.cskaoyan.service;

import com.cskaoyan.bean.Order;
import com.cskaoyan.bean.OrderItem;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    boolean createOrder(Order order) throws SQLException;

    void createOrderItem(OrderItem orderItem) throws SQLException;

    List<Order> findOrdersByUid(int uid) throws SQLException;

    boolean updateOrderState(String oid, String state) throws SQLException;
}
