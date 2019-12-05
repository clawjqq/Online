package com.chen.dao.impl;

import com.chen.dao.UserDao;
import com.chen.utils.DruidUtils;
import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @program: Online
 * @description:
 * @author: chenliang
 * @date: 2019-12-01 21:38
 **/

public class UserDaoImpl implements UserDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
    @Override
    public boolean login(String username, String password) {

        String sql = "select count(1) from user where username=? and password=?";

        int result = jdbcTemplate.queryForObject(sql, Integer.class, username, password);

        return result==1 ? true : false;
    }
}
