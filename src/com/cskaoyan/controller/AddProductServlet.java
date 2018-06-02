package com.cskaoyan.controller;

import com.cskaoyan.bean.Product;
import com.cskaoyan.service.ProductService;
import com.cskaoyan.service.impl.ProductServiceImpl;
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

@WebServlet(name = "AddProductServlet", urlPatterns = "/admin/AddProductServlet")
public class AddProductServlet extends HttpServlet {

    ProductService productService = new ProductServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1、解析请求，将商品信息封装到一个Map中
        Map<String, String> parameterMap = getParameterMap(request, response);
        Product product = new Product();
        if ( parameterMap!= null){
            try {
                BeanUtils.populate(product, parameterMap);
                boolean ret = productService.addProduct(product);
                if (ret) {
                    //添加成功
                    request.getRequestDispatcher("/admin/ProductServlet?op=findAllProduct&num=1").forward(request,response);
                } else {
                    //添加失败
                    response.getWriter().println("添加失败");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Map<String, String> getParameterMap(HttpServletRequest request, HttpServletResponse response) {

        //所有上传的图片，都放在images/product文件夹中
        String realPath = request.getServletContext().getRealPath("/images/product");
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
            while (iterator.hasNext()){
                FileItem item = iterator.next();
                if (item.isFormField()){
                    //处理普通表单数据
                    processFormField(item, parameterMap);
                }else {
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

        //构建文件名，解决文件重名问题

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

    private void processFormField(FileItem item, Map<String, String> parameterMap) throws UnsupportedEncodingException {
        String fieldName = item.getFieldName();
        if ("pid".equals(fieldName)){
            parameterMap.put("pid", item.getString("utf-8"));
        }else if ("pname".equals(fieldName)){
            parameterMap.put("pname", item.getString("utf-8"));
        }else if ("estoreprice".equals(fieldName)){
            parameterMap.put("estoreprice", item.getString("utf-8"));
        }else if ("markprice".equals(fieldName)){
            parameterMap.put("markprice", item.getString("utf-8"));
        }else if ("pnum".equals(fieldName)){
            parameterMap.put("pnum", item.getString("utf-8"));
        }else if ("cid".equals(fieldName)){
            parameterMap.put("cid", item.getString("utf-8"));
        }else if ("desc".equals(fieldName)){
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
