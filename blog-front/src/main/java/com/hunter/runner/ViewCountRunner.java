package com.hunter.runner;

import com.hunter.constants.RedisConstants;
import com.hunter.domain.entity.Article;
import com.hunter.mapper.ArticleMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 浏览量统计启动器
 * 程序启动后，会执行该类中的 run 方法
 *
 * @author Hunter
 * @since 2025/2/6
 */
@Component
@Slf4j
public class ViewCountRunner implements CommandLineRunner {
    @Resource
    private ArticleMapper articleMapper;

    @Resource(name = "customRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public void run(String... args) throws Exception {
        log.info("ViewCountRunner start...");
        // 不使用任何查询条件，从数据库中获取所有博文信息
        List<Article> articles = articleMapper.selectList(null);

        Map<String, Long> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), Article::getViewCount));

        // 将id、浏览量存入redis
        redisTemplate.opsForHash().putAll(RedisConstants.ARTICLE_VIEW_COUNT, viewCountMap);
    }
}
