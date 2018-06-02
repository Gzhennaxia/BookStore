package com.cskaoyan.controller;

import com.cskaoyan.bean.User;
import com.cskaoyan.bean.UserFormBean;
import com.cskaoyan.service.UserService;
import com.cskaoyan.service.impl.UserServiceImpl;
import com.cskaoyan.utils.SendJMail;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "UserServlet", urlPatterns = "/user/UserServlet")
public class UserServlet extends HttpServlet {

    UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //
        String op = request.getParameter("op");
        if (op != null) {
            switch (op) {
                case "register":
                    register(request, response);
                    break;
                case "isValidUsername":
                    isValidUsername(request, response);
                    break;
                case "activeUser":
                    activeUser(request, response);
                    break;
                case "updateUser":
                    updateUser(request, response);
                    break;
                case "login":
                    login(request, response);
                    break;
                case "logout":
                    logout(request, response);
                    break;
            }
        }

    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

//    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
////        String uid = request.getParameter("uid");
////        String nickname = request.getParameter("nickname");
////        String password = request.getParameter("password");
////        String email = request.getParameter("email");
////
////        User user = new User();
////
////        user.setUid(Integer.parseInt(uid));
////        user.setNickname(nickname);
////        user.setPassword(password);
////        user.setEmail(email);
//
//        UserFormBean userFormBean = new UserFormBean();
//        User user = new User();
//        String uid = request.getParameter("uid");
//        user.setUid(Integer.parseInt(uid));
//        Map<String, String[]> parameterMap = request.getParameterMap();
//        try {
//            BeanUtils.populate(userFormBean, parameterMap);
//            String errorMsg = userFormBean.varify();
//            if (errorMsg == null) {
//                //将日期字符串转换为Date类型
//                ConvertUtils.register(new DateLocaleConverter(), Date.class);
//                //将userFormBean复制到user中
//                BeanUtils.copyProperties(user, userFormBean);
//                //设置更新日期为当前日期
//                user.setUpdatetime(new Date());
//
//                boolean update = userService.updateUser(user);
//                if (update) {
//                    //修改成功
//                    response.getWriter().println("修改成功");
//                    HttpSession session = request.getSession();
//                    User oldUser = (User) session.getAttribute("user");
//
//                    response.setHeader("refresh", "1;" + request.getContextPath() + "/user/personal.jsp");
//                } else {
//                    //修改失败
//                    response.getWriter().println("修改失败");
//                    response.setHeader("refresh", "1;" + request.getContextPath() + "/user/personal.jsp");
//                }
//            }else {
//                response.getWriter().println(errorMsg);
//                response.setHeader("refresh", "1;" + request.getContextPath() + "/user/personal.jsp");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//
//    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {

        UserFormBean userFormBean = new UserFormBean();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            BeanUtils.populate(userFormBean, parameterMap);
            String errorMsg = userFormBean.varify();
            if (errorMsg == null) {
                //将日期字符串转换为Date类型
                ConvertUtils.register(new DateLocaleConverter(), Date.class);
                //将userFormBean复制到user中
                BeanUtils.copyProperties(user, userFormBean);
                //设置更新日期为当前日期
//                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                String format = dateFormat.format(new Date());
                user.setUpdatetime(new Date());

                BeanUtils.copyProperties(user, userFormBean);
                boolean update = userService.updateUser(user);
                if (update) {
                    //修改成功
                    response.getWriter().println("修改成功");
                    response.setHeader("refresh", "1;" + request.getContextPath() + "/user/personal.jsp");
                } else {
                    //修改失败
                    response.getWriter().println("修改失败");
                    response.setHeader("refresh", "1;" + request.getContextPath() + "/user/personal.jsp");
                }
            } else {
                response.getWriter().println(errorMsg);
                response.setHeader("refresh", "1;" + request.getContextPath() + "/user/personal.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }


    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String checkcode_session = (String) request.getSession().getAttribute("checkcode_session");
        String verifyCode = request.getParameter("verifyCode");
        if (verifyCode == null && !checkcode_session.equals(verifyCode)) {
            //验证码不正确
            response.getWriter().println("验证码不正确");
            response.setHeader("refresh", "1;" + request.getContextPath() + "/user/login.jsp");
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            try {
                User user = userService.findUser(username, password);

                if (user == null) {
                    //未注册
                    response.getWriter().println("用户名或密码不正确");
                    response.setHeader("refresh", "1;" + request.getContextPath() + "/user/login.jsp");

                } else if (user.getState() == 0) {
                    //未激活
                    response.getWriter().println("您的账户为还未激活");
                    response.setHeader("refresh", "1;" + request.getContextPath() + "/user/login.jsp");

                } else {
                    //登陆成功
                    //记录用户登录状态
                    HttpSession userState = request.getSession();
                    userState.setAttribute("user", user);
                    response.sendRedirect(request.getContextPath() + "/index.jsp");

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private void activeUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String activecode = request.getParameter("activecode");
        try {
            boolean activeSuccess = userService.activeUser(activecode);
            if (activeSuccess) {
                //激活成功
                response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            } else {
                //激活失败
                response.getWriter().println("激活失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    private void isValidUsername(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        if (username != null && !"".equals(username)) {
            try {
                boolean isValidUsername = userService.isValidUsername(username);
                if (isValidUsername) {
                    //该用户名已存在
                    response.getWriter().print("该用户名已存在,请重新注册");
                } else {
                    //新用户
                    response.getWriter().print("ok");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserFormBean userFormBean = new UserFormBean();
        User user = new User();
        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            BeanUtils.populate(userFormBean, parameterMap);
            boolean isValid = userFormBean.validate();
            if (isValid) {
                //注册信息有效

                //将日期字符串转换为Date类型
                ConvertUtils.register(new DateLocaleConverter(), Date.class);
                //将userFormBean复制到user中
                BeanUtils.copyProperties(user, userFormBean);
                //设置注册日期为当前日期
                user.setUpdatetime(new Date());
                //注册
                boolean register = userService.register(user);
                if (register) {
                    //注册成功
                    response.getWriter().println("注册成功");
                    response.setHeader("refresh", "1;" + request.getContextPath() + "/user/login.jsp");
                } else {
                    //注册失败
                    response.getWriter().println("注册失败");
                }
            } else {
                //注册信息有误
                request.setAttribute("msg", userFormBean);
                request.getRequestDispatcher("regist.jsp").forward(request, response);
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
