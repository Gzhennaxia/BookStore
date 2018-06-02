package com.cskaoyan.controller;

import com.cskaoyan.bean.Category;
import com.cskaoyan.service.CategoryService;
import com.cskaoyan.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "InitLoadCategoryServlet", urlPatterns = "/admin/InitLoadCategoryServlet",
        loadOnStartup = 1)
public class InitLoadCategoryServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        CategoryService categoryService = new CategoryServiceImpl();
        try {
            List<Category> allCategory = categoryService.findAllCategory();

            getServletContext().setAttribute("categories", allCategory);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
