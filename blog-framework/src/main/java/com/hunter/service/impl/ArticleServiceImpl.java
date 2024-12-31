package com.hunter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.Article;
import com.hunter.mapper.ArticleMapper;
import com.hunter.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Hunter
 * @description 针对表【hunter_article(文章表)】的数据库操作Service实现
 * @createDate 2024-12-29 10:42:42
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, "0") // 已发布
                .eq(Article::getDelFlag, 0) // 未删除
                .orderByDesc(Article::getViewCount); // 按浏览量排序

        // 最多只查询10条
        Page<Article> page = new Page<>(1, 10);
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();
        return ResponseResult.okResult(articles);
    }

}




