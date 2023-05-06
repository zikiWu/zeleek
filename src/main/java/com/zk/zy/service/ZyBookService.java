package com.zk.zy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zk.zy.dto.BookSearchDTO;
import com.zk.zy.model.ZyBook;
import com.zk.zy.vo.ZyBookVO;

public interface ZyBookService {

    Page<ZyBook> searchByKey(BookSearchDTO bookSearchDTO);

    ZyBookVO searchById(Long id);

    void dataToEs();
}
