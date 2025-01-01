package com.hunter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.Article;

/**
 * @author Hunter
 * @description 针对表【hunter_article(文章表)】的数据库操作Service
 * @createDate 2024-12-29 10:42:42
 */
public interface ArticleService extends IService<Article> {

    /**
     * 查询热门文章
     * @return 热门文章
     */
    ResponseResult<?> hotArticleList();
}
