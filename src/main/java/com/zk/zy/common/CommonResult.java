package com.zk.zy.common;

import lombok.Data;

@Data
public class CommonResult<T> {

    private Boolean success;

    private String message;

    private String code;

    private T data;

    public static <T> CommonResult<T> createSuccessDTO() {
        CommonResult<T> dto = new CommonResult<>();
        dto.setSuccess(true);
        dto.setCode(ResponseCode.SUCCESS);
        return dto;
    }

    public static <T> CommonResult<T> createSuccessDTO(T t) {
        CommonResult<T> dto = new CommonResult<T>();
        dto.setSuccess(true);
        dto.setCode(ResponseCode.SUCCESS);
        dto.setData(t);
        return dto;
    }

    public static <T> CommonResult<T> createFailureDTO() {
        CommonResult<T> dto = new CommonResult<>();
        dto.setSuccess(false);
        dto.setCode(ResponseCode.FAIL);
        return dto;
    }

    public static <T> CommonResult<T> createFailureDTO(String message) {
        CommonResult<T> dto = new CommonResult<>();
        dto.setSuccess(false);
        dto.setCode(ResponseCode.FAIL);
        dto.setMessage(message);
        return dto;
    }

    public static <T> CommonResult<T> createFailureDTO(T t) {
        CommonResult<T> dto = new CommonResult<>();
        dto.setSuccess(false);
        dto.setCode(ResponseCode.FAIL);
        dto.setData(t);
        return dto;
    }

    public static <T> CommonResult<T> createTipDTO(String message, T data) {
        CommonResult<T> dto = new CommonResult<>();
        dto.setSuccess(false);
        dto.setCode(ResponseCode.TIP);
        dto.setMessage(message);
        dto.setData(data);
        return dto;
    }

    public static <T> CommonResult<T> createCommonDTO(String code, String message, T data) {
        CommonResult<T> dto = new CommonResult<>();
        dto.setSuccess(false);
        dto.setCode(code);
        dto.setMessage(message);
        dto.setData(data);
        return dto;
    }
}