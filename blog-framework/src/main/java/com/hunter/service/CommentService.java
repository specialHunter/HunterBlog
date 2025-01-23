package com.hunter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.Comment;
import com.hunter.domain.vo.PageVo;

/**
 * @author Hunter
 * @description 针对表【hunter_comment(评论表)】的数据库操作Service
 * @createDate 2025-01-21 17:11:08
 */
public interface CommentService extends IService<Comment> {
    /**
     * 获取评论列表
     * @param commentType 评论类型：0：文章评论；1：友链评论
     * @param articleId 文章ID
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return
     */
    ResponseResult<PageVo> getCommentList(String commentType, Long articleId, int pageNum, int pageSize);

    /**
     * 添加评论
     *
     * @param comment 评论实体
     * @return 添加结果
     */
    <T> ResponseResult<T> addComment(Comment comment);
}
