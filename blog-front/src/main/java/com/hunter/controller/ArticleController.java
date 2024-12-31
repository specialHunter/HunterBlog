package com.hunter.controller;

import com.hunter.domain.ResponseResult;
import com.hunter.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hunter
 * @since 2024/12/30
 */

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    /*@RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Article> test() {
        return articleService.list();
    }*/

    /**
     * 查询热门文章
     *
     * @return 热门文章
     */
    @RequestMapping(value = "/hotArticleList", method = RequestMethod.GET)
    public ResponseResult hotArticleList() {
        ResponseResult result = articleService.hotArticleList();
        return result;
    }
}
