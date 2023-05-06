package com.zk.zy.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zk.zy.dto.BookSearchDTO;
import com.zk.zy.mapper.ZyBookMapper;
import com.zk.zy.model.ZyBook;
import com.zk.zy.service.ZyBookService;
import com.zk.zy.vo.ZyBookVO;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ZyBookServiceImpl implements ZyBookService {

    @Autowired
    private ZyBookMapper zyBookMapper;

    @Resource(name = "elasticsearchTemplate")
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public boolean createIndexAndPushMapping() {

        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(ZyBook.class);
        //创建索引
        boolean a = indexOperations.create();
        if (a){
            //生成映射
            Document mapping = indexOperations.createMapping();
            //推送映射
            boolean b = indexOperations.putMapping(mapping);
            return b;
        }else {
            return a;
        }
    }

    private void syncBookDataToEs(){
        List<ZyBook> bookList = zyBookMapper.getAll();
        elasticsearchRestTemplate.save(bookList);
    }


    @Override
    public Page<ZyBook> searchByKey(BookSearchDTO bookSearchDTO) {

        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.should(QueryBuilders.matchQuery("content", bookSearchDTO.getKey()));
        boolQueryBuilder.should(QueryBuilders.matchQuery("title", bookSearchDTO.getKey()));
        query.withQuery(boolQueryBuilder);
        //设置分页
        PageRequest pageRequest = PageRequest.of(bookSearchDTO.getPageNum(), bookSearchDTO.getPageSize());
        query.withPageable(pageRequest);
        //设置高亮
        withHighlight(query);
//        query.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
        SearchHits<ZyBook> searchHits = elasticsearchRestTemplate.search(query.build(), ZyBook.class);
        List<ZyBook> zyBookList = new ArrayList<>();
        Page<ZyBook> iPage = new Page();
        iPage.setTotal(searchHits.getTotalHits());
        iPage.setRecords(zyBookList);
        iPage.setPages((searchHits.getTotalHits() / bookSearchDTO.getPageSize()) + 1);
        iPage.setCurrent(bookSearchDTO.getPageNum());
        for (SearchHit<ZyBook> searchHit : searchHits) {
            ZyBook zyBook = searchHit.getContent();
            zyBookList.add(zyBook);
        }
        return iPage;
    }

    @Override
    public ZyBookVO searchById(Long id) {
        ZyBook zyBook = zyBookMapper.selectByPrimaryKey(id);
        ZyBookVO zyBookVO = new ZyBookVO();
        BeanUtils.copyProperties(zyBook, zyBookVO);
        return zyBookVO;
    }

    @Override
    public void dataToEs() {
        createIndexAndPushMapping();
        syncBookDataToEs();
    }

    /**
     * 添加高亮条件
     * @param searchQuery
     */
    private void withHighlight(NativeSearchQueryBuilder searchQuery){
        HighlightBuilder.Field hfield= new HighlightBuilder.Field("content")
                .preTags("<em style='color:red'>")
                .postTags("</em>")
                .fragmentSize(100);
        searchQuery.withHighlightFields(hfield);
    }
}
