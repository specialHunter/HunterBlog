package com.hunter.controller;

import com.hunter.annotation.SystemLog;
import com.hunter.constants.SystemConstants;
import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.Comment;
import com.hunter.domain.vo.PageVo;
import com.hunter.service.CommentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 评论控制器
 *
 * @author Hunter
 * @since 2025/1/15
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;


    /**
     * 获取文章评论列表
     *
     * @param articleId 文章id
     * @param pageNum   页码
     * @param pageSize  每页数量
     * @return 评论列表
     */
    @GetMapping("/commentList")
    @SystemLog(businessName = "获取文章评论列表")
    public ResponseResult<PageVo> getCommentList(Long articleId, int pageNum, int pageSize) {
        return commentService.getCommentList(SystemConstants.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    /**
     * 添加评论
     *
     * @param comment 评论实体
     * @return 添加结果
     */
    @PostMapping
    public <T> ResponseResult<T> addComment(@RequestBody Comment comment) { // json请求数据需要加@RequestBody注解
        return commentService.addComment(comment);
    }

    /**
     * 获取友链评论列表
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 友链评论列表
     */
    @GetMapping("/linkCommentList")
    public ResponseResult<PageVo> getLinkCommentList(int pageNum, int pageSize) {
        return commentService.getCommentList(SystemConstants.LINK_COMMENT, null, pageNum, pageSize);
    }
}
