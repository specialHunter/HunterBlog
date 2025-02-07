package config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hunter.HunterBlogApplication;
import com.hunter.constants.RedisConstants;
import com.hunter.domain.entity.Article;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hunter
 * @since 2025/2/6
 */
@SpringBootTest(classes = HunterBlogApplication.class)
public class RedisTest {
    @Resource(name = "customRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test() throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("1", 1L);
        map.put("2", 12345678956776L);

        redisTemplate.opsForHash().putAll(RedisConstants.ARTICLE_VIEW_COUNT, map);

        BoundHashOperations<String, String, Number> boundHashOps =
                redisTemplate.boundHashOps(RedisConstants.ARTICLE_VIEW_COUNT);
        Map<String, Number> viewCountMap = boundHashOps.entries();
        assert viewCountMap != null;
        List<Article> articleList = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()),
                        entry.getValue().longValue()))
                .toList();
    }
}
