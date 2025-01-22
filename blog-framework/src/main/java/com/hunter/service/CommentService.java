package com.hunter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.Comment;

/**
 * @author Hunter
 * @description 针对表【hunter_comment(评论表)】的数据库操作Service
 * @createDate 2025-01-21 17:11:08
 */
public interface CommentService extends IService<Comment> {
    ResponseResult<?> getCommentList(long articleId, int pageNum, int pageSize);
}
