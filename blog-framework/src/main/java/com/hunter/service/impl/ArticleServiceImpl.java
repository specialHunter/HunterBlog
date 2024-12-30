package com.hunter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunter.domain.entity.Article;
import com.hunter.mapper.ArticleMapper;
import com.hunter.service.ArticleService;
import org.springframework.stereotype.Service;

/**
 * @author Hunter
 * @description 针对表【hunter_article(文章表)】的数据库操作Service实现
 * @createDate 2024-12-29 10:42:42
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

}




