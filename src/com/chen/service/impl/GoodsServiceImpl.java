package com.chen.service.impl;

import com.chen.dao.GoodsDao;
import com.chen.dao.impl.GoodsDaoImpl;
import com.chen.entry.Goods;
import com.chen.entry.GoodsVo;
import com.chen.service.GoodsService;

import java.util.List;

/**
 * @program: WebOnline-Thired
 * @description:
 * @author: chenliang
 * @date: 2019-11-21 14:54
 **/

public class GoodsServiceImpl implements GoodsService {
    GoodsDao goodsDao = new GoodsDaoImpl();

    //展示商品页面
    @Override
    public List<Goods> getGoodsList(GoodsVo goodsVo) {

        return goodsDao.getGoodsList(goodsVo);
    }

    //点击编辑，根据ID获取 goods 展示到 addGoods 页面上
    @Override
    public Goods getGoodsById(String goodsId) {

        return goodsDao.getGoodsById(goodsId);
    }



    //向数据库添加或跟新内容,在此处判断是否有ID值
    @Override
    public int saveOrUpdate(Goods goods) {
        int id = goods.getId();
        if (id == 0) {
            return goodsDao.save(goods);
        } else {
            return goodsDao.update(goods);
        }
    }

    @Override
    public int deleteById(int id) {
        return goodsDao.deleteGoods(id);
    }

    //分页需要，查询里面有多少条符合条件
    @Override
    public int getCount(GoodsVo goodsVo) {
        return goodsDao.getCount(goodsVo);
    }

    //checkbox批量删除
    @Override
    public void batchDelete(String[] ids) {

        goodsDao.batchDelete(ids);
    }


}
