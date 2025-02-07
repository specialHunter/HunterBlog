package com.hunter.controller;

import com.hunter.domain.ResponseResult;
import com.hunter.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章控制器
 *
 * @author Hunter
 * @since 2024/12/30
 */

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    /**
     * 分页查询指定分类下的文章列表
     *
     * @param pageNum 第几页
     * @param pageSize 一页多少文章
     * @param categoryId 分类id
     * @return 文章列表
     */
    @RequestMapping(value = "/articleList", method = RequestMethod.GET)
    public ResponseResult<?> articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    /**
     * 查询热门文章
     *
     * @return 热门文章
     */
    @RequestMapping(value = "/hotArticleList", method = RequestMethod.GET)
    public ResponseResult<?> hotArticleList() {
        return articleService.hotArticleList();
    }

    /**
     * 查询文章详情
     *
     * @param articleId 文章id
     * @return 文章详情
     */
    @RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
    public ResponseResult<?> getArticleDetail(@PathVariable Long articleId) {
        return articleService.getArticleDetail(articleId);
    }

    /**
     * 更新指定文章的浏览量
     * @param articleId 文章id
     * @return 更新结果
     */
    @PutMapping("/updateViewCount/{articleId}")
    public ResponseResult<?> updateViewCount(@PathVariable Long articleId) {
        return articleService.updateViewCount(articleId);
    }
}
