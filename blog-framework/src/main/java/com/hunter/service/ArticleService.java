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
     *
     * @return 热门文章
     */
    ResponseResult<?> hotArticleList();

    /**
     * 获取分类列表
     *
     * @param pageNum 第几页
     * @param pageSize 一页多少文章
     * @param categoryId 分类id
     * @return 文章列表
     */
    ResponseResult<?> articleList(Integer pageNum, Integer pageSize, Long categoryId);
}
