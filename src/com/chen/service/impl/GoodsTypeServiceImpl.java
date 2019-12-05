package com.chen.service.impl;

import com.chen.dao.GoodsTypeDao;
import com.chen.dao.impl.GoodsTypeDaoImpl;
import com.chen.entry.GoodsType;
import com.chen.service.GoodsTypeService;

import java.util.List;

/**
 * @program: WebOnline-Thired
 * @description:
 * @author: chenliang
 * @date: 2019-11-22 01:37
 **/
public class GoodsTypeServiceImpl implements GoodsTypeService {
    GoodsTypeDao goodsTypeDao = new GoodsTypeDaoImpl();
    @Override
    public List<GoodsType> getGoodsTypeList() {
        return goodsTypeDao.getGoodsTypeList();
    }


}
