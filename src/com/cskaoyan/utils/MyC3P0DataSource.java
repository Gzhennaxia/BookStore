package com.cskaoyan.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;

public class MyC3P0DataSource {
    //包装一个DataSource
    static DataSource dataSource;

    //初始化静态变量
    static{

        dataSource = new ComboPooledDataSource("mysql");

    }

    //获取dataSource
    public static DataSource getDataSource(){

        return dataSource;

    }
}
