package com.zk.zy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zk.zy.common.CommonResult;
import com.zk.zy.dto.BookSearchDTO;
import com.zk.zy.model.ZyBook;
import com.zk.zy.service.ZyBookService;
import com.zk.zy.vo.ZyBookVO;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/zkBook")
public class zkBookController {

    @Autowired
    private ZyBookService zyBookService;


    @GetMapping(value = "/searchByKey")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数没有填好"), @ApiResponse(code = 404, message = "请求路径没有找到")})
    public CommonResult<Page<ZyBook>> searchByKey(BookSearchDTO bookSearchDTO) {
        Page<ZyBook> page = zyBookService.searchByKey(bookSearchDTO);
        return CommonResult.createSuccessDTO(page);
    }

    @GetMapping(value = "/searchById")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数没有填好"), @ApiResponse(code = 404, message = "请求路径没有找到")})
    public CommonResult<ZyBookVO> searchById(Long id) {
        ZyBookVO zyBookVO = zyBookService.searchById(id);
        return CommonResult.createSuccessDTO(zyBookVO);
    }

    @GetMapping(value = "/dataToEs")
    public CommonResult dataToEs() {
        zyBookService.dataToEs();
        return CommonResult.createSuccessDTO();
    }

}
