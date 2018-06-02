package com.cskaoyan.controller;

import com.cskaoyan.bean.*;
import com.cskaoyan.dao.CartDao;
import com.cskaoyan.dao.impl.CartDaoImpl;
import com.cskaoyan.service.CartService;
import com.cskaoyan.service.OrderService;
import com.cskaoyan.service.ProductService;
import com.cskaoyan.service.impl.CartServiceImpl;
import com.cskaoyan.service.impl.OrderServiceImpl;
import com.cskaoyan.service.impl.ProductServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "OrderServlet", urlPatterns = "/OrderServlet")
public class OrderServlet extends HttpServlet {

    OrderService orderService = new OrderServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if (op != null) {
            switch (op) {
                case "placeOrder":
                    placeOrder(request, response);
                    break;
                case "findMyOrder":
                    findMyOrder(request, response);
                    break;
                case "cancelOrder":
                    cancelOrder(request, response);
                    break;
            }
        }
    }

    private void cancelOrder(HttpServletRequest request, HttpServletResponse response) {
        String oid = request.getParameter("oid");
        String state = request.getParameter("state");
        try {
            boolean ret = orderService.updateOrderState(oid, state);
            if (ret){
                //更新成功
                findMyOrder(request, response);
            }else {
                //更新失败
                response.getWriter().println("更新失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findMyOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        OrderService orderService = new OrderServiceImpl();
        try {
            List<Order> orders = orderService.findOrdersByUid(user.getUid());
            if (orders != null) {
                //查找成功
                request.setAttribute("orders", orders);
                request.getRequestDispatcher("/myOrders.jsp").forward(request, response);
            } else {
                //查找失败
                response.getWriter().println("查找失败");
                response.setHeader("refresh", "1;" + request.getContextPath() + "/index.jsp");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void placeOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Order order = new Order();
        try {

            BeanUtils.populate(order, parameterMap);
            //验证收件人，电话，地址信息
            String errorMsg = order.validate();
            if (errorMsg == null) {
                //信息填写正确

                CartService cartService = new CartServiceImpl();
                ProductService productService = new ProductServiceImpl();

                //创建订单
                UUID uuid = UUID.randomUUID();
                order.setOid(uuid.toString());
                boolean ret = orderService.createOrder(order);

                if (ret) {
                    //订单创建成功
                    //根据uid找到该用户的购物车
                    ShoppingCart cart = cartService.findCartByUid(order.getUid());

                    String[] pids = request.getParameterValues("ids");
                    for (String pid : pids) {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setOid(order.getOid());
                        orderItem.setPid(pid);

                        //根据根据sid和pid找到shoppingItem
                        ShoppingItem shoppingItem = cartService.findShoppingItem(cart.getSid(), pid);

                        //获取pnum字段
                        int snum = shoppingItem.getSnum();
                        orderItem.setBuynum(snum);

                        //创建订单项
                        orderService.createOrderItem(orderItem);

                        //更新库存
                        Product productByPid = productService.findProductByPid(pid);
                        productByPid.setPnum(productByPid.getPnum() - snum);
                        productService.updateProduct(productByPid);

                        //从购物车中删除这条购物项
                        cartService.delItemByItemId("" + shoppingItem.getItemid());
                    }

                    response.getWriter().println("订单创建成功");
                    response.setHeader("refresh", "1;" + request.getContextPath() + "/shoppingcart.jsp");

                } else {
                    //订单创建失败
                    response.getWriter().println("订单创建失败");
                    response.setHeader("refresh", "1;" + request.getContextPath() + "/placeOrder.jsp");
                }

            } else {
                //信息填写有误
                response.getWriter().println("信息填写有误");
                response.setHeader("refresh", "1;" + request.getContextPath() + "/placeOrder.jsp");

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
