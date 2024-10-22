package com.zk.leek.dto;

import lombok.Data;

@Data
public class BookSearchDTO {
    private String key;

    private Integer pageNum;

    private Integer pageSize;

}
