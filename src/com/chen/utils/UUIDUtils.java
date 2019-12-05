package com.chen.utils;

import java.util.UUID;

/**
 * @program: FileUpload
 * @description:
 * @author: chenliang
 * @date: 2019-11-29 01:13
 **/

public class UUIDUtils {

    public static String getUUID(){
        String uuid = UUID.randomUUID().toString(); //这个UUID是36位的  有四个-，去掉了就是32位的
        uuid = uuid.replace("-", ""); //去掉 -
        return uuid;
    }

    public static void main(String[] args) {
        System.out.println(getUUID());

    }




}
