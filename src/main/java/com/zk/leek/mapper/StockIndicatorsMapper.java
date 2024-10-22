package com.zk.leek.mapper;

import com.zk.leek.model.StockIndicators;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockIndicatorsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockIndicators record);

    int insertSelective(StockIndicators record);

    StockIndicators selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockIndicators record);

    int updateByPrimaryKey(StockIndicators record);

    int batchInsert(@Param("list") List<StockIndicators> list);
}