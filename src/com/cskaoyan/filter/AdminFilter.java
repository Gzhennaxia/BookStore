package com.cskaoyan.filter;

import com.sun.deploy.net.HttpResponse;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AdminFilter", urlPatterns = "/admin/*")
public class AdminFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession();
        Object admin = session.getAttribute("admin");
        if (session != null && admin != null) {
            chain.doFilter(req, resp);
        } else if (requestURI.endsWith("index.jsp") ||
                (requestURI.endsWith("AdminServlet") && "login".equals(request.getParameter("op"))) ||
                requestURI.endsWith(".js") ||
                requestURI.endsWith(".css") ||
                requestURI.endsWith(".gif")) {

            chain.doFilter(req, resp);

        } else {

            response.sendRedirect(request.getContextPath() + "/admin/index.jsp");

        }


    }

    public void init(FilterConfig config) throws ServletException {

    }

}
