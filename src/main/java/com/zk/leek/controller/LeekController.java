package com.zk.leek.controller;

import com.zk.leek.common.CommonResult;
import com.zk.leek.service.LeekService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/leek")
public class LeekController {

    @Autowired
    private LeekService leekService;


    @GetMapping(value = "/init")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数没有填好"), @ApiResponse(code = 404, message = "请求路径没有找到")})
    public CommonResult init() {

        return CommonResult.createSuccessDTO();
    }

}
