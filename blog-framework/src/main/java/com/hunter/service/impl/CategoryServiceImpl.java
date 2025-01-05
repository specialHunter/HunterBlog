package com.hunter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunter.constants.SystemConstants;
import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.Article;
import com.hunter.domain.entity.Category;
import com.hunter.domain.vo.CategoryVo;
import com.hunter.mapper.CategoryMapper;
import com.hunter.service.ArticleService;
import com.hunter.service.CategoryService;
import com.hunter.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Hunter
 * @description 针对表【hunter_category(分类表)】的数据库操作Service实现
 * @createDate 2025-01-02 22:20:53
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {

    @Resource
    private ArticleService articleService;


    @Override
    public ResponseResult<?> getCategoryList() {
        // 展示有发布正式文章的分类

        // 先查文章表 状态为已发布、未删除的文章
        LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
        articleQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_RELEASE);
        List<Article> articleList = articleService.list(articleQueryWrapper);
        // 获取文章的分类id，去重
        Set<Long> categoryIdSet = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
        // 查询分类表
        List<Category> categoryList = listByIds(categoryIdSet);
        categoryList = categoryList.stream()
                .filter(category -> SystemConstants.CATEGORY_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        // 封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}




