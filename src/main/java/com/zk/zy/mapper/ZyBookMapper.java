package com.zk.zy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zk.zy.model.ZyBook;

import java.util.List;

public interface ZyBookMapper extends BaseMapper<ZyBook> {
    int deleteByPrimaryKey(Long id);

    int insert(ZyBook record);

    int insertSelective(ZyBook record);

    ZyBook selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ZyBook record);

    int updateByPrimaryKey(ZyBook record);

    List<ZyBook> getAll();
}