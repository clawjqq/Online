package com.chen.dao;

import com.chen.entry.Goods;
import com.chen.entry.GoodsVo;

import java.util.List;

/**
 * @program: WebOnline-Thired
 * @description:
 * @author: chenliang
 * @date: 2019-11-21 14:59
 **/

public interface GoodsDao {
    //展示商品页面
    List<Goods> getGoodsList(GoodsVo goodsVo);

    //点击编辑，根据ID获取 goods 展示到 addGoods 页面上
    Goods getGoodsById(String goodsId);

    //向数据库添加或更新内容
    int save(Goods goods);

    //修改数据库中的内容
    int update(Goods goods);

    //删除商品
    int deleteGoods(int id);

    //分页需要，查询数据库中有多少条
    int getCount(GoodsVo goodsVo);

    //checkbox批量删除
    void batchDelete(String[] ids);
}
