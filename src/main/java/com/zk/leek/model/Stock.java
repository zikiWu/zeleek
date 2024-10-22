package com.zk.leek.model;

import lombok.Data;

@Data
public class Stock {
    private Long id;

    private String code;

    private String name;

    private String type;

    private String symbol;
}