package com.cskaoyan.dao.impl;

import com.cskaoyan.bean.Category;
import com.cskaoyan.bean.Product;
import com.cskaoyan.dao.ProductDao;
import com.cskaoyan.utils.MyC3P0DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    private static QueryRunner queryRunner;

    static {
        queryRunner = new QueryRunner(MyC3P0DataSource.getDataSource());
    }

    @Override
    public boolean addProduct(Product product) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSource.getDataSource());
        int update = queryRunner.update("insert into product values(?, ?, ?, ?, ?, ?, ?, ?)",
                product.getPid(),
                product.getPname(),
                product.getEstoreprice(),
                product.getMarkprice(),
                product.getPnum(),
                product.getCid(),
                product.getImgurl(),
                product.getDesc());

        return update == 1;
    }

    @Override
    public int getTotalRecordsNum() throws SQLException {

        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSource.getDataSource());
        Long query = (Long) queryRunner.query("select count(*) from product", new ScalarHandler());
        return query.intValue();
    }

    @Override
    public List<Product> getPartProduct(int limit, int offset) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSource.getDataSource());
        List<Product> query = queryRunner.query("select * from product limit ? offset ?",
                new BeanListHandler<Product>(Product.class),
                limit,
                offset);
        if (query != null){
            for (Product product : query) {
                int cid = product.getCid();
                Category category = queryRunner.query("select * from category where cid = ?",
                        new BeanHandler<Category>(Category.class),
                        cid);
                product.setCategory(category);
            }
            return query;
        }
        return null;
    }

    @Override
    public boolean deleteOne(String pid) throws SQLException {
        int update = queryRunner.update("delete from product where pid = ?", pid);
        return update == 1;
    }

    @Override
    public Product findProductByPid(String pid) throws SQLException {
        Product product = queryRunner.query("select * from product where pid = ?",
                new BeanHandler<Product>(Product.class),
                pid);
        return product;
    }

    @Override
    public boolean updateProduct(Product product) throws SQLException {
        int update = queryRunner.update("update product set pname = ?, estoreprice = ?, markprice = ?, pnum = ?, cid = ?, imgurl = ?, `desc` = ? where pid = ?;",
                product.getPname(),
                product.getEstoreprice(),
                product.getMarkprice(),
                product.getPnum(),
                product.getCid(),
                product.getImgurl(),
                product.getDesc(),
                product.getPid());
        return update == 1;
    }

    @Override
    public List<Product> getProductByMultiCondition(String pid, String pname, String cid, String minPrice, String maxPrice, int limit, int offset) throws SQLException {

        String sql = "select * from product where 1=1";

        if (pid != null && !pid.isEmpty()){
            sql = sql + " and pid = " + pid;
        }
        if (pname != null && !pname.isEmpty()){
            sql = sql + " and pname = " + pname;
        }
        if (cid != null && !cid.isEmpty()){
            sql = sql + " and cid = " + cid;
        }
        if (minPrice != null && !minPrice.isEmpty()){
            if (Integer.parseInt(minPrice) > 0){
                sql = sql + " and estoreprice >= " + minPrice;
            }
        }
        if (maxPrice != null && !maxPrice.isEmpty()){
            if (Integer.parseInt(maxPrice) > 0){
                sql = sql + " and estoreprice <= " + maxPrice;
            }
        }

        sql += " limit " + limit + " offset " + offset;

        List<Product> query = queryRunner.query(sql, new BeanListHandler<Product>(Product.class));

        return query;
    }

    @Override
    public int getTotalRecordsNumByMultiCondition(String pid, String pname, String cid, String minPrice, String maxPrice) throws SQLException {


        String sql = "select count(*) from product where 1=1";

        if (pid != null && !pid.isEmpty()){
            sql = sql + " and pid = " + pid;
        }
        if (pname != null && !pname.isEmpty()){
            sql = sql + " and pname = " + pname;
        }
        if (cid != null && !cid.isEmpty()){
            sql = sql + " and cid = " + cid;
        }
        if (minPrice != null && !minPrice.isEmpty()){
            if (Integer.parseInt(minPrice) > 0){
                sql = sql + " and estoreprice >= " + minPrice;
            }
        }
        if (maxPrice != null && !maxPrice.isEmpty()){
            if (Integer.parseInt(maxPrice) > 0){
                sql = sql + " and estoreprice <= " + maxPrice;
            }
        }

        Long query = (Long) queryRunner.query(sql, new ScalarHandler());

        return query.intValue();
    }

    @Override
    public List<Product> getTopProduct() throws SQLException {

        //假设top商品为价格最贵的三个商品
        List<Product> query = queryRunner.query("select * from product order by estoreprice desc limit 3;", new BeanListHandler<Product>(Product.class));

        return query;
    }

    @Override
    public List<Product> findHotProducts() throws SQLException {

        //假设hot商品为库存最少的十个商品
        List<Product> query = queryRunner.query("select * from product order by pnum asc limit 10;", new BeanListHandler<Product>(Product.class));

        return query;
    }

    @Override
    public List<Product> findProductByCid(String cid) throws SQLException {

        List<Product> query = queryRunner.query("select * from product where cid = ?",
                new BeanListHandler<Product>(Product.class),
                cid);
        return query;
    }

    @Override
    public List<Product> findProductByName(String keyword) throws SQLException {

        List<Product> query = queryRunner.query("select * from product where pname like ? or `desc` like ?",
                new BeanListHandler<Product>(Product.class),
                "%" + keyword + "%",
                "%" + keyword + "%");
        return query;
    }

    @Override
    public List<Product> getPartHotProduct(int limit, int offset) throws SQLException {

        //假设hot商品为库存最少的10条记录
        List<Product> query = queryRunner.query ("select * from product order by pnum asc limit ? offset ?;",
                new BeanListHandler<Product>(Product.class),
                limit,
                offset);

        return query;
    }

    @Override
    public int getTotalProductByCidNum(String cid) throws SQLException {
        Long query = (Long) queryRunner.query("select count(*) from product where cid = ?",
                new ScalarHandler(),
                cid);
        return query.intValue();
    }

    @Override
    public List<Product> getPartProductByCid(String cid, int limit, int offset) throws SQLException {


        List<Product> query = queryRunner.query("select * from product where cid = ? limit ? offset ?",
                new BeanListHandler<Product>(Product.class),
                cid,
                limit,
                offset);
        return query;
    }

    @Override
    public int getTotalProductByKeywordNum(String keyword) throws SQLException {
        Long query = (Long) queryRunner.query("select count(*) from product where pname like ? or `desc` like ?",
                new ScalarHandler(),
                "%" + keyword + "%",
                "%" + keyword + "%");
        return query.intValue();

    }

    @Override
    public List<Product> getPartProductByKeyword(String keyword, int limit, int offset) throws SQLException {

        List<Product> query = queryRunner.query("select * from product where pname like ? or `desc` like ? limit ? offset ?",
                new BeanListHandler<Product>(Product.class),
                "%" + keyword + "%",
                "%" + keyword + "%",
                limit,
                offset);
        return query;

    }
}
