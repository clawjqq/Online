package com.chen.dao.impl;

import com.chen.dao.GoodsDao;
import com.chen.entry.Goods;
import com.chen.entry.GoodsVo;
import com.chen.utils.DruidUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @program: WebOnline-Thired
 * @description:
 * @author: chenliang
 * @date: 2019-11-21 15:00
 **/

public class GoodsDaoImpl implements GoodsDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());

    //展示商品页面
    @Override
    public List<Goods> getGoodsList(GoodsVo goodsVo) {
        // 内联查询，将t_goodstype内容内联到 goods表中
        String sql = "select g.*,t.typename from goods g,t_goodstype t where g.typeId = t.id";
        // 如果用户输入了查询条件，需要根据查询条件进行查询
        if (goodsVo.getGoodsName() != null && !goodsVo.getGoodsName().equals("")) {
            sql += " and g.goodsName like '%" + goodsVo.getGoodsName() + "%'";
        }
        if (goodsVo.getDate() != null && !goodsVo.getDate().equals("")) {
            sql += " and g.uploadTime = '" + goodsVo.getDate() + "'";
        }
        if (goodsVo.getTypeId() != 0) {
            sql += " and g.typeId =" + goodsVo.getTypeId();
        }

        sql += " limit " + goodsVo.getBegin() + "," + goodsVo.getRows();

        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Goods>(Goods.class));
    }

    //点击添加，向数据库中插人在 form表单中input框中的值
    @Override
    public int save(Goods goods) {

        String sql = "insert into goods(goodsName, price, typeId, comment, uploadTime, imagePath) values(?,?,?,?,?,?)";
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(date);
        return  jdbcTemplate.update(sql,goods.getGoodsName(),goods.getPrice(),goods.getTypeId(),goods.getComment(),dateStr,goods.getImagePath());
    }

    //点击编辑，根据ID获取 goods 展示到 addGoods 页面上
    @Override
    public Goods getGoodsById(String goodsId) {

        //String sql = "select * from goods g where g.id ="+ goodsId;
        //增加关联查询typename，方便展示到默认框中
                                                                               //SQL拼接，=号后面没有加空格，一直报错
        //String sql = "select g.*,t.typename from goods g,t_goodstype t where g.id ="+goodsId+"and g.typeId = t.id";
        String sql = "select g.*,t.typename from goods g,t_goodstype t where g.id = "+goodsId+" and g.typeId = t.id";

        /*List<Goods> listE = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Goods>(Goods.class));
        return listE.get(0);*/
        // 不用上面的方法，下面的方法是通过SQL语句拿到1个对象，然后再将Goods这个对象返回去
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Goods>(Goods.class));
    }

    // 点击编辑，更改原goodsID所属的商品内容
    @Override
    public int update(Goods goods) {
        String sql = "";
        if (goods.getImagePath().equals("")) {
           sql = "update goods set goodsName=?,price=?,typeId=?,comment=? where id=?";
            return jdbcTemplate.update(sql, goods.getGoodsName(), goods.getPrice(), goods.getTypeId(), goods.getComment(), goods.getId());
        }else {
           sql = "update goods set goodsName=?,price=?,typeId=?,comment=?,imagePath=? where id=?";
            return jdbcTemplate.update(sql, goods.getGoodsName(), goods.getPrice(), goods.getTypeId(), goods.getComment(), goods.getImagePath(), goods.getId());
        }

    }

    @Override
    public int deleteGoods(int id) {
        String sql = "delete from goods where id=?";
        return jdbcTemplate.update(sql, id);
    }

    //查询数据库中有多少条
    @Override
    public int getCount(GoodsVo goodsVo) {

        String sql = "select count(1) from goods g,t_goodstype t where g.typeId = t.id";

        if (goodsVo.getGoodsName() != null && !goodsVo.getGoodsName().equals("")) {
            sql += " and g.goodsName like '%" + goodsVo.getGoodsName() + "%'";
        }
        if (goodsVo.getDate() != null && !goodsVo.getDate().equals("")) {
            sql += " and g.uploadTime = '" + goodsVo.getDate() + "'";
        }
        if (goodsVo.getTypeId() != 0) {
            sql += " and g.typeId =" + goodsVo.getTypeId();
        }

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    //checkbox批量删除
    @Override
    public void batchDelete(String[] ids) {

        String sql = "delete from goods where id = ?";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, ids[i]); // 1 代表第一个问号
            }

            @Override
            public int getBatchSize() {
                return ids.length;
            }

        });



    }


}
