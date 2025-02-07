package com.hunter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunter.constants.RedisConstants;
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
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Resource(name = "customRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ResponseResult<?> hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 查询已发布的文章，并按浏览量排序
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_RELEASE)
                .orderByDesc(Article::getViewCount);

        // 最多只查询10条
        Page<Article> page = new Page<>(1, 10);
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();

        batchUpdateViewCount(articles);
        // bean拷贝，字段名 和 类型 都需要一致，否则无法拷贝
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.success(articleVos);
    }

    @Override
    public ResponseResult<?> articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 指定分类的已发布文章，以 置顶文章 为先进行排序
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId)
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_RELEASE)
                .orderByDesc(Article::getIsTop);

        // 分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        List<Article> articles = page(page, queryWrapper).getRecords();

        // todo: Article类不需要特意添加 需要@TableField(exist = false)注解的属性，直接在ArticleVo里设置即可
        // 根据articleList中每个categoryId查询分类名称
        articles = articles.stream()
                .peek(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        batchUpdateViewCount(articles);
        List<ArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, ArticleVo.class);


        PageVo pageVo = new PageVo(articleVos, page.getTotal());
        return ResponseResult.success(pageVo);
    }

    /**
     * 批量从redis中获取文章的浏览量，更新到articles中
     *
     * @param articles 文章列表
     */
    private void batchUpdateViewCount(List<Article> articles) {
        // 获取分页下的文章ID
        List<String> articleIds = articles.stream()
                .map(articleVo -> articleVo.getId().toString())
                .collect(Collectors.toList());
        // 从redis中获取对应的viewCount数据
        BoundHashOperations<String, String, Number> boundHashOps =
                redisTemplate.boundHashOps(RedisConstants.ARTICLE_VIEW_COUNT);
        // 获取的viewCounts数据和articleIds顺序是对应的
        List<Number> viewCounts = boundHashOps.multiGet(articleIds);
        if (viewCounts != null) {
            // 将redis中存储的viewCount数据设置到articleVos中
            for (int i = 0; i < articles.size(); i++) {
                if (viewCounts.get(i) != null) {
                    articles.get(i).setViewCount(viewCounts.get(i).longValue());
                }
            }
        }
    }

    @Override
    public ResponseResult<?> getArticleDetail(Long articleId) {
        Article article = getById(articleId);

        // 从redis中获取viewCount数据
        HashOperations<String, String, Number> hashOperations = redisTemplate.opsForHash();
        Long viewCount =
                Objects.requireNonNull(
                                hashOperations.get(RedisConstants.ARTICLE_VIEW_COUNT, articleId.toString()))
                        .longValue();
        article.setViewCount(viewCount);

        Category category = categoryService.getById(article.getCategoryId());
        article.setCategoryName(category.getName());

        ArticleDetailVo articleDetail = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);

        return ResponseResult.success(articleDetail);
    }

    @Override
    public ResponseResult<?> updateViewCount(Long articleId) {
        // 将redis中对应文章的浏览量+1，注意key都是String类型存储，需要转换
        redisTemplate.opsForHash().increment(RedisConstants.ARTICLE_VIEW_COUNT, articleId.toString(), 1);

        return ResponseResult.success();
    }
}




