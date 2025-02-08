package com.hunter.cron;

import com.hunter.config.UpdateViewCountConfig;
import com.hunter.constants.RedisConstants;
import com.hunter.domain.entity.Article;
import com.hunter.mapper.ArticleMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 更新浏览量的定时任务
 *
 * @author Hunter
 * @since 2025/2/6
 */
@EnableScheduling // 开启定时任务
@Component
@Slf4j
public class UpdateViewCountCronJob {
    @Resource(name = "customRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private UpdateViewCountConfig updateViewCountConfig;

    /**
     * 每5分钟更新一次浏览量
     * 从redis写回到数据库
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public void updateViewCount() {
        log.info("========== 更新浏览量 ==========");

        // 从redis读出浏览量，需要先指定泛型，如果直接调用到entries只能得到Object类型
        BoundHashOperations<String, String, Number> boundHashOps =
                redisTemplate.boundHashOps(RedisConstants.ARTICLE_VIEW_COUNT);
        Map<String, Number> viewCountMap = boundHashOps.entries();
        assert viewCountMap != null;
        List<Article> articleList = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()),
                        entry.getValue().longValue()))
                .toList();

        // 设置 是否为更新浏览量操作 的标识
        updateViewCountConfig.setIsUpdateViewCount(true);
        // 更新到数据库
        articleMapper.updateById(articleList);
        // 重置标识
        updateViewCountConfig.setIsUpdateViewCount(false);
    }
}