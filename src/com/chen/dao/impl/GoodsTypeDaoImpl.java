package com.chen.dao.impl;

import com.chen.dao.GoodsTypeDao;
import com.chen.entry.GoodsType;
import com.chen.utils.DruidUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
/**
 * @program: WebOnline-Thired
 * @description:
 * @author: chenliang
 * @date: 2019-11-22 01:46
 **/
public class GoodsTypeDaoImpl implements GoodsTypeDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
    @Override
    public List<GoodsType> getGoodsTypeList() {

        // IFNULL(expr1，expr2) ，

        String sql = "select id, typename, level, NULLIF(pid,0) from t_goodstype";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<GoodsType>(GoodsType.class));
    }

}
