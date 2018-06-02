package com.cskaoyan.controller;

import com.cskaoyan.bean.Category;
import com.cskaoyan.service.CategoryService;
import com.cskaoyan.service.impl.CategoryServiceImpl;
import com.cskaoyan.utils.Page;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CategoryServlet", urlPatterns = "/admin/CategoryServlet")
public class CategoryServlet extends HttpServlet {

    CategoryService categoryService = new CategoryServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        //所有的servlet都要会遇到编码问题，就抽出来放EncodingFilter里
//        request.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html;charset=UTF-8");

        String op = request.getParameter("op");
        if (op != null) {
            switch (op) {
                case "addCategory":
                    addCategory(request, response);
                    break;
                case "findPartCategory":
                    String num = request.getParameter("num");
                    findPartCategory(request, response, num);
                    break;
                case "updateCategory":
                    updateCategory(request, response);
                    break;
                case "deleteOne":
                    deleteOne(request, response);
                    break;
                case "findAllCategory":
                    findAllCategory(request, response);
                    break;

            }
        }
    }

    private void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try {
            List<Category> allCategory = categoryService.findAllCategory();
            if (allCategory != null) {
                //分类查找成功
                getServletContext().setAttribute("categories", allCategory);

                //使用刷新页面的方式跳转，浏览器会重新发送一个新的请求，则在request域对象中存放的属性就会丢失
                //故应该通过请求转发的方式进行跳转
                //response.setHeader("refresh", "1;" + request.getContextPath() + "/admin/category/categoryList.jsp");
                request.getRequestDispatcher("/admin/product/addProduct.jsp").forward(request, response);

            } else {
                //分类查找失败
                response.getWriter().println("分类查找失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteOne(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String cid = request.getParameter("cid");
        try {
            boolean ret = categoryService.deleteOne(cid);
            if (ret) {
                //删除成功
                List<Category> allCategory = categoryService.findAllCategory();
                if (allCategory != null) {
                    //分类查找成功
                    updateCategories(this);

                    //使用刷新页面的方式跳转，浏览器会重新发送一个新的请求，则在request域对象中存放的属性就会丢失
                    //故应该通过请求转发的方式进行跳转
                    //response.setHeader("refresh", "1;" + request.getContextPath() + "/admin/category/categoryList.jsp");
                    request.getRequestDispatcher(request.getContextPath() + "/admin/category/categoryList.jsp").forward(request, response);

                }

            } else {
                //删除失败
                response.getWriter().println("删除失败");
                //response.setHeader("refresh", "1;" + request.getContextPath() + "/admin/category/categoryList.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        Category category = new Category();
        try {
            BeanUtils.populate(category, request.getParameterMap());
            boolean ret = categoryService.updateCategory(category);
            if (ret) {
                //更新成功
                updateCategories(this);
                findPartCategory(request, response, "1");

            } else {
                //更新失败
                response.getWriter().println("更新失败");
                response.setHeader("refresh", "1;" + request.getContextPath() + "/admin/category/updateCategory.jsp");

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void findPartCategory(HttpServletRequest request, HttpServletResponse response, String num) throws IOException, ServletException {
//        try {
//            List<Category> allCategory = categoryService.findPartCategory();
//            if (allCategory != null) {
//                //分类查找成功
//                request.setAttribute("allCategory", allCategory);
//
//                //使用刷新页面的方式跳转，浏览器会重新发送一个新的请求，则在request域对象中存放的属性就会丢失
//                //故应该通过请求转发的方式进行跳转
//                //response.setHeader("refresh", "1;" + request.getContextPath() + "/admin/category/categoryList.jsp");
//                request.getRequestDispatcher(request.getContextPath() + "/admin/category/categoryList.jsp").forward(request, response);
//
//            } else {
//                //分类查找失败
//                response.getWriter().println("分类查找失败");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        try {
            Page<Category> page = categoryService.findPage(num);
            if (page != null) {
                //查找类别成功
                request.setAttribute("page", page);

                //注意路径问题
                //请求转发与包含只能在同一个应用间进行，所以相对路径的根目录默认为当前应用
                //request.getRequestDispatcher(request.getContextPath() + "/admin/category/categoryList.jsp").forward(request, response);
                request.getRequestDispatcher("/admin/category/categoryList.jsp").forward(request, response);

            } else {
                //查找类别失败
                response.getWriter().println("查找类别失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String cname = request.getParameter("cname");
        try {
            boolean ret = categoryService.addCategory(cname);
            if (ret) {
                //分类添加成功
//                response.setHeader("refresh",
//                        "1;" + request.getContextPath() + "/CategoryServlet?op=findPartCategory");
                //更新内存中的categories
                updateCategories(this);
                findPartCategory(request, response, "1");

            } else {
                //分类添加失败
                response.getWriter().println("分类添加失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCategories(CategoryServlet categoryServlet) throws SQLException {

        List<Category> allCategory = categoryService.findAllCategory();

        getServletContext().setAttribute("categories", allCategory);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
