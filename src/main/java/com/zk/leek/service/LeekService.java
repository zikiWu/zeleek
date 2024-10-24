package com.zk.leek.service;


import com.zk.leek.model.Stock;
import com.zk.leek.vo.StockVO;

import java.util.List;

public interface LeekService {

    void init();

    void stat();

    List<Stock> getList();

    StockVO getRandom();

    void statOther();
}
