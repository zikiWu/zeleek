package com.zk.leek.vo;

import lombok.Data;

@Data
public class IndicatorsDesc {

    private String ename;
    private String name;
    private String value;
    private String status;
    private String originValue;

    // toString
    @Override
    public String toString() {
        return "IndicatorsDesc{" +
                "ename='" + ename + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", status='" + status + '\'' +
                ", originValue='" + originValue + '\'' +
                '}';
    }

}
