package com.cskaoyan.controller;

import com.cskaoyan.bean.Product;
import com.cskaoyan.service.ProductService;
import com.cskaoyan.service.impl.ProductServiceImpl;
import com.cskaoyan.utils.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "MainServlet", urlPatterns = "/MainServlet")
public class MainServlet extends HttpServlet {

    private ProductService productService = new ProductServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        try {
//            List<Product> topProducts = findTopProducts();
//            request.setAttribute("topProducts", topProducts);
//
//            List<Product> hotProducts = findHotProducts();
//            request.setAttribute("hotProducts", hotProducts);
//
//            if (topProducts != null && hotProducts != null){
//                request.getRequestDispatcher("/index.jsp").forward(request, response);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try {
            List<Product> topProducts = findTopProducts();
            request.setAttribute("topProducts", topProducts);

            String num = request.getParameter("num");
            if (num == null){
                num = "1";
            }

            Page<Product> page = productService.findPartHotProducts(num);
            request.setAttribute("page", page);

            if (topProducts != null && page != null) {
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private List<Product> findHotProducts() throws SQLException {

        return productService.findHotProducts();
    }

    private List<Product> findTopProducts() throws SQLException {

        List<Product> topProducts = productService.findTopProducts();
        return topProducts;
    }
}
