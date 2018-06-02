package com.cskaoyan.controller;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.service.AdminService;
import com.cskaoyan.service.impl.AdminServiceImpl;
import com.cskaoyan.utils.MyC3P0DataSource;
import com.cskaoyan.utils.Page;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(name = "AdminServlet", urlPatterns = "/admin/AdminServlet")
public class AdminServlet extends HttpServlet {

    AdminService adminService;
    String contentPath;

    @Override
    public void init() throws ServletException {
        contentPath = getServletContext().getContextPath();
        adminService = new AdminServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if (op != null) {
            switch (op) {
                case "addAdmin":
                    addAdmin(request, response);
                    break;
                case "findAllAdmin":
                    String num = request.getParameter("num");
                    findAllAdmin(request, response, num);
                    break;
                case "updateAdmin":
                    updateAdmin(request, response);
                    break;
                case "login":
                    login(request, response);
                    break;
            }
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Admin admin = new Admin(username, password);
        try {
            boolean isExistAdmin = adminService.isExistAdmin(admin);

            if (isExistAdmin) {
                //登录成功
                HttpSession session = request.getSession();
                session.setAttribute("admin", admin);

                //请求转发实在同一个应用间转换的，对客户端是透明的，请求还是同一个请求，其url不会变
                //request.getRequestDispatcher("/admin/main.jsp").forward(request, response);
                response.sendRedirect(request.getContextPath() + "/admin/main.jsp");

            } else {
                //登录失败
                response.getWriter().println("登录失败,请重新登录");
                response.setHeader("refresh", "1;" + request.getContextPath() + "/admin/index.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateAdmin(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Admin admin = new Admin();
        try {
            BeanUtils.populate(admin, parameterMap);
            boolean ret = adminService.updateAdmin(admin);
            if (ret) {
                //更新成功
                findAllAdmin(request, response, "1");

            } else {
                //更新失败
                response.getWriter().println("更新失败");
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findAllAdmin(HttpServletRequest request, HttpServletResponse response, String num) throws IOException, ServletException {

        try {
            Page<Admin> page = adminService.findPage(num);
            if (page != null) {
                //查找成功
                request.setAttribute("page", page);

                //注意路径问题
                //请求转发与包含只能在同一个应用间进行，所以相对路径的根目录默认为当前应用
                //request.getRequestDispatcher(request.getContextPath() + "/admin/admin/adminList.jsp").forward(request, response);
                request.getRequestDispatcher("/admin/admin/adminList.jsp").forward(request, response);

            } else {
                //查找失败
                response.getWriter().println("查找失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Admin admin = new Admin();
        try {
            BeanUtils.populate(admin, parameterMap);
            boolean isRegistered = adminService.isRegistered(admin);
            if (isRegistered) {
                //已存在的用户
                response.getWriter().println("该用户已存在");
            } else {
                //新用户
                boolean ret = adminService.addAdmin(admin);
                if (ret) {
                    //添加成功
                    findAllAdmin(request, response, "1");
                    //request.getRequestDispatcher(request.getContextPath() + "/admin/admin/adminList.jsp?num = 1").forward(request, response);
                } else {
                    //添加失败
                    response.getWriter().println("添加失败");
                }
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
