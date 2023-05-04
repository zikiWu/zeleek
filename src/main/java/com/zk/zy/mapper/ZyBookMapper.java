package com.zk.zy.mapper;

import com.zk.zy.model.ZyBook;

public interface ZyBookMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ZyBook record);

    int insertSelective(ZyBook record);

    ZyBook selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ZyBook record);

    int updateByPrimaryKey(ZyBook record);
}