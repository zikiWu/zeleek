package com.zk.leek.model;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class StockIndicators {
    private Long id;

    private Long stockId;

    private String stockCode;

    private Date statDate;

    /**
     * 当前价格
     */
    private BigDecimal price;

    /**
     * 涨幅
     */
    private String ratiostr;

    /**
     * 涨幅
     */
    private BigDecimal ratio;

    /**
     * 涨幅价
     */
    private String increase;

    /**
     * 今开
     */
    private BigDecimal open;

    /**
     * 最高
     */
    private BigDecimal high;

    /**
     * 成交量
     */
    private Long volume;

    /**
     * 昨收
     */
    private BigDecimal preClose;

    /**
     * 最低
     */
    private BigDecimal low;

    /**
     * 成交额
     */
    private BigDecimal amount;

    /**
     * 换手率
     */
    private BigDecimal turnoverRatio;

    /**
     * 市盈率
     */
    private BigDecimal peratio;

    /**
     * 总市值
     */
    private BigDecimal capitalization;

    /**
     * 量比
     */
    private BigDecimal volumeRatio;

    /**
     * 市净率
     */
    private BigDecimal lyr;

    /**
     * 总股本
     */
    private BigDecimal totalShareCapital;

    /**
     * 委比
     */
    private BigDecimal weibiRatio;

    /**
     * 均价
     */
    private BigDecimal avgPrice;

    /**
     * 流通市值
     */
    private BigDecimal currencyValue;

    /**
     * 涨停价
     */
    private BigDecimal limitUp;

    /**
     * 涨跌幅
     */
    private BigDecimal priceLimit;

    /**
     * 流通股本
     */
    private BigDecimal circulatingCapital;

    /**
     * 跌停价
     */
    private BigDecimal limitDown;

    /**
     * 振幅
     */
    private BigDecimal amplitudeRatio;

    /**
     * 内盘
     */
    private Long inside;

    /**
     * 价销比
     */
    private String pricesaleRatio;

    /**
     * 市销率
     */
    private BigDecimal bvRatio;

    /**
     * 外盘
     */
    private Long outside;

    /**
     * 52周最高
     */
    private BigDecimal w52High;

    /**
     * 52周最低
     */
    private BigDecimal w52Low;

    /**
     * 同股同权
     */
    private String profitFlag;

    /**
     * 投票权
     */
    private String voteRightFlag;

    /**
     * VIE结构
     */
    private String ptccFlag;

    /**
     * 是否注册制
     */
    private String regFlag;

    private String allData;
}