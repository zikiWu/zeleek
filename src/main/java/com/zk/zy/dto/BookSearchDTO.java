package com.zk.zy.dto;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class BookSearchDTO {
    private String key;

    private Integer pageNum;

    private Integer pageSize;

}
