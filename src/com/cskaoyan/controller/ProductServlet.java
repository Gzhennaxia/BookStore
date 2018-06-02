package com.cskaoyan.controller;

import com.cskaoyan.bean.Category;
import com.cskaoyan.bean.Product;
import com.cskaoyan.service.CategoryService;
import com.cskaoyan.service.ProductService;
import com.cskaoyan.service.impl.CategoryServiceImpl;
import com.cskaoyan.service.impl.ProductServiceImpl;
import com.cskaoyan.utils.Page;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "ProductServlet", urlPatterns = "/admin/ProductServlet")
public class ProductServlet extends HttpServlet {

    ProductService productService = new ProductServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if (op != null) {
            switch (op) {
//                case "addProduct":
//                    addProduct(request, response);
//                    break;
                case "findAllProduct":
                    String num = request.getParameter("num");
                    findAllProduct(request, response, num);
                    break;
                case "deleteOne":
                    deleteOne(request, response);
                    break;
                case "findProductForUpdate":
                    findProductForUpdate(request, response);
                    break;
                case "updateProduct":
                    updateProduct(request, response);
                    break;
                case "multiConditionSearch":
                    multiConditionSearch(request, response);
                    break;

            }
        }
    }

    private void multiConditionSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        String pname = request.getParameter("pname");
        String cid = request.getParameter("cid");
        String minprice = request.getParameter("minprice");
        String maxprice = request.getParameter("maxprice");

        String num = request.getParameter("num");
        if (num==null||num.isEmpty()){
            num="1";
        }

        try {

            Page<Product> page = productService.findProductByMultiCondition(pid, pname, cid, minprice, maxprice, num);
            if (page != null){
                //查找成功
                request.setAttribute("page", page);
                request.getRequestDispatcher("/admin/product/searchProductList.jsp").forward(request, response);
            }else {
                //查找失败
                response.getWriter().println("查找失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, String> parameterMap = getParameterMap(request, response);
        Product product = new Product();
        try {
            BeanUtils.populate(product, parameterMap);
            boolean ret = productService.updateProduct(product);
            if (ret) {
                //修改成功
                //response.getWriter().println("修改成功");
                request.getRequestDispatcher("/admin/ProductServlet?op=findAllProduct&num=1").forward(request, response);
            } else {
                //修改失败
                response.getWriter().println("修改失败");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> getParameterMap(HttpServletRequest request, HttpServletResponse response) {

        //所有上传的图片，都放在admin/images/product文件夹中
        String realPath = request.getServletContext().getRealPath("/admin/images/product");
        File productImgBase = new File(realPath);
        productImgBase.mkdirs();

        //封装用户信息的Map
        Map<String, String> parameterMap = new HashMap<>();

        //1、配置文件上传工具类ServletFileUpload
        ServletFileUpload fileUpload = getServletFileUpload();

        //如果上传的文件名有中文，则使用如下编码解决乱码问题
        fileUpload.setHeaderEncoding("utf-8");

        //2、Parse the request
        try {
            List<FileItem> fileItems = fileUpload.parseRequest(request);
            Iterator<FileItem> iterator = fileItems.iterator();
            while (iterator.hasNext()) {
                FileItem item = iterator.next();
                if (item.isFormField()) {
                    //处理普通表单数据
                    processFormField(item, parameterMap);
                } else {
                    //处理上传文件
                    processUploadFile(item, parameterMap, productImgBase);
                }
            }
            return parameterMap;
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void processUploadFile(FileItem item, Map<String, String> parameterMap, File productImgBase) throws Exception {

        //如果上传了新的图片，则修改imgurl
        if (!"".equals(item.getName())) {
            //1、生成一个相对于当前jvm唯一的随机数（128位，按16进制表示）
            UUID uuid = UUID.randomUUID();
            String s = uuid.toString();

            //2、在原来的文件名的基础上增加这个随机数
            String newFileName = s + item.getName();

            //将新的文件名封装到Map中
            parameterMap.put("imgurl", newFileName);

            //3、构建最终的文件存储路径
            File file = new File(productImgBase, newFileName);

            //4、将上传的文件写入该文件中
            item.write(file);
        }

    }

    private void processFormField(FileItem item, Map<String, String> parameterMap) throws UnsupportedEncodingException {
        String fieldName = item.getFieldName();
        if ("pid".equals(fieldName)) {
            parameterMap.put("pid", item.getString("utf-8"));
        } else if ("pname".equals(fieldName)) {
            parameterMap.put("pname", item.getString("utf-8"));
        } else if ("estoreprice".equals(fieldName)) {
            parameterMap.put("estoreprice", item.getString("utf-8"));
        } else if ("markprice".equals(fieldName)) {
            parameterMap.put("markprice", item.getString("utf-8"));
        } else if ("pnum".equals(fieldName)) {
            parameterMap.put("pnum", item.getString("utf-8"));
        } else if ("cid".equals(fieldName)) {
            parameterMap.put("cid", item.getString("utf-8"));
        } else if ("imgurl".equals(fieldName)) {
            parameterMap.put("imgurl", item.getString("utf-8"));
        } else if ("desc".equals(fieldName)) {
            parameterMap.put("desc", item.getString("utf-8"));
        }
    }

    private ServletFileUpload getServletFileUpload() {

        //1、Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        //2、Configure a repository (to ensure a secure temp location is used)
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);

        //3、Create a new file upload handler
        return new ServletFileUpload(factory);

    }

    private void findProductForUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        try {
            Product product = productService.findProductByPid(pid);
            if (product != null) {
                CategoryService categoryService = new CategoryServiceImpl();
                List<Category> allCategory = categoryService.findAllCategory();
                request.setAttribute("categories", allCategory);
                request.setAttribute("product", product);
                request.getRequestDispatcher("/admin/product/updateProduct.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void deleteOne(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pid = request.getParameter("pid");
        try {
            boolean ret = productService.deleteOne(pid);
            if (ret) {
                //删除成功
                response.getWriter().println("删除成功");
                response.setHeader("refresh", "1;" + request.getContextPath() +
                        "/admin/ProductServlet?op=findAllProduct&num=1");
            } else {
                //删除失败
                response.getWriter().println("删除失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void findAllProduct(HttpServletRequest request, HttpServletResponse response, String num) throws ServletException, IOException {

        try {
            Page<Product> page = productService.findPage(num);
            if (page != null) {
                //查找成功
                request.setAttribute("page", page);

                //注意路径问题
                //请求转发与包含只能在同一个应用间进行，所以相对路径的根目录默认为当前应用
                //request.getRequestDispatcher(request.getContextPath() + "/admin/product/productList.jsp").forward(request, response);
                request.getRequestDispatcher("/admin/product/productList.jsp").forward(request, response);

            } else {
                //查找失败
                response.getWriter().println("查找失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    private void addProduct(HttpServletRequest request, HttpServletResponse response) {
//        Map<String, String[]> parameterMap = request.getParameterMap();
//        Product product = new Product();
//        try {
//            BeanUtils.populate(product, parameterMap);
//            boolean ret = productService.addProduct(product);
//            if (ret) {
//                //添加成功
//            } else {
//                //添加失败
//            }
//
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
