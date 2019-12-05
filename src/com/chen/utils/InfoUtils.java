package com.chen.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @program: Online
 * @description:
 * @author: chenliang
 * @date: 2019-12-01 23:22
 **/

public class InfoUtils {
    //只读取配置文件

    static Properties properties = null;

    static {

        properties = new Properties();

        InputStream inputStream = InfoUtils.class.getClassLoader().getResourceAsStream("info.properties");
        //小磊这么写的 URL inputStream2 = InfoUtils.class.getClassLoader().getSystemResource("/info.properties");

        try {
            properties.load(inputStream); //这样数据就进来了
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 要想访问就可以用  InfoUtils.properties.get....
    // 因为不好调，还需要new它，加上static
    public static String getParameter(String name){
        return InfoUtils.properties.getProperty(name);
    }





}
