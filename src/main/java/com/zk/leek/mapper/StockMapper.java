package com.zk.leek.mapper;

import com.zk.leek.model.Stock;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Stock record);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);

    int batchInsert(@Param("list") List<Stock> list);

    List<Stock> getAll();

    List<Stock> getNotData(@Param("date") String date);
}