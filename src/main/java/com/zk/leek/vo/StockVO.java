package com.zk.leek.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockVO {
    private Long id;

    private String code;

    private String name;

    private String type;

    private String symbol;

    private BigDecimal price;
}