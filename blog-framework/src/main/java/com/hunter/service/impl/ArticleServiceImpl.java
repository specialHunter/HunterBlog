package com.hunter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunter.constants.SystemConstants;
import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.Article;
import com.hunter.domain.entity.Category;
import com.hunter.domain.vo.ArticleDetailVo;
import com.hunter.domain.vo.ArticleVo;
import com.hunter.domain.vo.HotArticleVo;
import com.hunter.domain.vo.PageVo;
import com.hunter.mapper.ArticleMapper;
import com.hunter.service.ArticleService;
import com.hunter.service.CategoryService;
import com.hunter.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Hunter
 * @description 针对表【hunter_article(文章表)】的数据库操作Service实现
 * @createDate 2024-12-29 10:42:42
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Lazy // 延迟加载，解决循环依赖问题（springboot 2.6以上不支持循环依赖service）
    @Resource
    private CategoryService categoryService;

    @Override
    public ResponseResult<?> hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_RELEASE) // 已发布
                .orderByDesc(Article::getViewCount); // 按浏览量排序

        // 最多只查询10条
        Page<Article> page = new Page<>(1, 10);
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();

        // bean拷贝，字段名 和 类型 都需要一致，否则无法拷贝
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult<?> articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId) // 指定分类
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_RELEASE) // 文章已发布
                .orderByDesc(Article::getIsTop); // 以 置顶文章 为先进行排序

        // 分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        List<Article> articleList = page(page, queryWrapper).getRecords();

        // 根据articleList中每个categoryId查询分类名称
        articleList = articleList.stream()
                .peek(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        List<ArticleVo> articleVos = BeanCopyUtils.copyBeanList(articleList, ArticleVo.class);

        PageVo pageVo = new PageVo(articleVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult<?> getArticleDetail(Long articleId) {
        Article article = getById(articleId);

        Category category = categoryService.getById(article.getCategoryId());
        article.setCategoryName(category.getName());

        ArticleDetailVo articleDetail = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);

        return ResponseResult.okResult(articleDetail);
    }

}




