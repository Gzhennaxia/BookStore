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

@WebServlet(name = "UserProductServlet", urlPatterns = "/user/UserProductServlet")
public class UserProductServlet extends HttpServlet {

    ProductService productService = new ProductServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String op = request.getParameter("op");
        if (op != null) {
            switch (op) {
                case "byCid":
                    findPartProductByCid(request, response, request.getParameter("cid"));
                    break;
                case "findProductById":
                    findProductById(request, response, request.getParameter("pid"));
                    break;
                case "findProductByKeyword":
                    findPartProductByKeyword(request, response, request.getParameter("keyword"));
                    break;
            }
        }
    }

    private void findPartProductByKeyword(HttpServletRequest request, HttpServletResponse response, String keyword) throws ServletException, IOException {
        try {
            String num = request.getParameter("num");
            if (num == null){
                //首次查询
                num = "1";
            }
            Page<Product> page = productService.findPartProductByKeyword(num, keyword);
            if (page != null) {
                //查找成功
                request.setAttribute("page", page);
                request.setAttribute("keyword", keyword);
                request.getRequestDispatcher("/products.jsp").forward(request, response);
            } else {
                //查找失败
                response.getWriter().println("查找失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void findPartProductByCid(HttpServletRequest request, HttpServletResponse response, String cid) throws IOException, ServletException {

        try {

            String num = request.getParameter("num");
            Page<Product> page = productService.findPartProductByCid(cid ,num);
            if (page != null) {
                //查找成功

                //当点击下一页等的时候需要用到属性cid，故在转发时需要将当前cid保存在request域对象中
                request.setAttribute("cid", cid);
                request.setAttribute("page", page);
                request.getRequestDispatcher("/products.jsp").forward(request, response);
            } else {
                //查找失败
                response.getWriter().println("查找失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void findProductByKeyword(HttpServletRequest request, HttpServletResponse response, String keyword) throws ServletException, IOException {
        try {
            List<Product> productsByName = productService.findPartProductByKeyword(keyword);
            if (productsByName != null) {
                //查找成功
                request.setAttribute("products", productsByName);
                request.setAttribute("keyword", keyword);
                request.getRequestDispatcher("/products.jsp").forward(request, response);
            } else {
                //查找失败
                response.getWriter().println("查找失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void findProductById(HttpServletRequest request, HttpServletResponse response, String pid) throws IOException, ServletException {
        try {
            Product productByPid = productService.findProductByPid(pid);
            if (productByPid != null) {
                //查找成功
                request.setAttribute("product", productByPid);
                request.getRequestDispatcher("/productdetail.jsp").forward(request, response);
            } else {
                //查找失败
                response.getWriter().println("查找失败");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void findProductByCid(HttpServletRequest request, HttpServletResponse response, String cid) throws ServletException, IOException {

        try {
            List<Product> productsByCid = productService.findProductByCid(cid);
            if (productsByCid != null) {
                //查找成功
                request.setAttribute("products", productsByCid);
                request.getRequestDispatcher("products.jsp").forward(request, response);
            } else {
                //查找失败
                response.getWriter().println("查找失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
