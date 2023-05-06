package com.zk.zy.vo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@Document(indexName = "blog", shards = 1, replicas = 1)
public class ZyBookVO {
    /**
    * 主键
    */
    @Id
    private Long id;

    /**
    * 标题
    */
    @Field(type = FieldType.Text)
    private String title;

    /**
    * 作者
    */
    @Field(type = FieldType.Text)
    private String author;

    /**
    * 朝代
    */
    @Field(type = FieldType.Keyword)
    private String dynasty;

    /**
    * 内容
    */
    @Field(type = FieldType.Text)
    private String content;

    /**
    * 创建时间
    */
    @Field(type=FieldType.Date, format= DateFormat.custom, pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Date createTime;

}