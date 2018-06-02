package com.cskaoyan.controller;

import com.cskaoyan.bean.ShoppingCart;
import com.cskaoyan.bean.User;
import com.cskaoyan.service.CartService;
import com.cskaoyan.service.impl.CartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CartServlet", urlPatterns = "/CartServlet")
public class CartServlet extends HttpServlet {

    CartService cartService = new CartServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if (op != null) {
            switch (op) {
                case "addCart":
                    addCart(request, response);
                    break;
                case "findCart":
                    findCart(request, response);
                    break;
                case "delItem":
                    delItem(request, response);
                    break;
                case "findShoppingCar":
                    findShoppingCar(request, response);
                    break;
            }
        }
    }

    private void findShoppingCar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        try {
            ShoppingCart cartByUid = cartService.findCartByUid(Integer.parseInt(uid));
            if (cartByUid != null){
                //查找成功
                request.setAttribute("shoppingCar", cartByUid);
                request.getRequestDispatcher("/placeOrder.jsp").forward(request, response);
            }else {
                //查找失败
                response.getWriter().println("查找失败");
                response.setHeader("refresh", "1;" + request.getContextPath() + "/CartServlet?op=findCart");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void delItem(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String uid = request.getParameter("uid");
        String itemid = request.getParameter("itemid");
        try {
            boolean ret = cartService.delItemByItemId(itemid);
            if (ret) {
                //删除成功
                response.getWriter().println("删除成功");
                response.setHeader("refresh", "1;" + request.getContextPath() + "/CartServlet?op=findCart");

            } else {
                //删除失败
                response.getWriter().println("删除失败");
                response.setHeader("refresh", "1;" + request.getContextPath() + "/CartServlet?op=findCart");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void findCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        try {
            ShoppingCart shoppingCart = cartService.findCartByUid(user.getUid());
            if (shoppingCart != null) {
                //查找成功
                request.setAttribute("shoppingCar", shoppingCart);
                request.getRequestDispatcher("/shoppingcart.jsp").forward(request, response);
            } else {
                //查找失败
                response.getWriter().println("查找失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void addCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pid = request.getParameter("pid");
        String uid = request.getParameter("uid");
        String snum = request.getParameter("snum");
        if (snum == null || "".equals(snum)) {
            snum = "1";
        }
        try {

            boolean ret = cartService.addCart(pid, uid, snum);
            if (ret) {
                //添加成功
                response.getWriter().println("商品添加成功");
                response.setHeader("refresh", "1;" + request.getContextPath() + "/CartServlet?op=findCart");
            } else {
                //添加失败
                response.getWriter().println("添加失败");
                response.setHeader("refresh", "1;" + request.getContextPath() + "/CartServlet?op=findCart");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
