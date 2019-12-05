package com.chen.utils;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @program: WebOnline-Two
 * @description:
 * @author: chenliang
 * @date: 2019-11-18 11:49
 **/

public class DruidUtils {
    static Properties properties = null;
    //加载properties文件
    static {
        properties = new Properties(); // hashtable ->  Map
        InputStream inputStream = DruidUtils.class.getClassLoader().getResourceAsStream("db.properties");
                                                                                       //db.properties
        try {
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 可以获取 dataSource
    public static DataSource getDataSource() {
        DataSource dataSource = null;
        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    // 可以获取 数据库连接对象
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

