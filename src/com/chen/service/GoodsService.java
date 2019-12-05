package com.chen.service;

import com.chen.entry.Goods;
import com.chen.entry.GoodsVo;

import java.util.List;

/**
 * @program: WebOnline-Thired
 * @description:
 * @author: chenliang
 * @create: 2019-11-21 14:53
 **/

public interface GoodsService {
    //展示商品页面
    List<Goods> getGoodsList(GoodsVo goodsVo);

    //向数据库添加或更新内容
    int saveOrUpdate(Goods goods);

    //点击编辑，根据ID获取 goods 展示到 addGoods 页面上
    Goods getGoodsById(String goodsId);

    int deleteById(int id);

    //分页需要 - 查询数据库中有多少条
    int getCount(GoodsVo goodsVo);

    //checkbox批量删除
    void batchDelete(String[] ids);
}
