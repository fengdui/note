package com.zheyue.encrypt.dao;

import com.zheyue.encrypt.entity.Material;

public interface MaterialMapper {
    int deleteByPrimaryKey(Integer materialid);

    int insert(Material record);

    int insertSelective(Material record);

    Material selectByPrimaryKey(Integer materialid);

    int updateByPrimaryKeySelective(Material record);

    int updateByPrimaryKey(Material record);

    String selectPathById(Integer materialid);
}