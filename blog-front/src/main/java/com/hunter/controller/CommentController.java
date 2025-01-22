package com.hunter.controller;

import com.hunter.domain.ResponseResult;
import com.hunter.service.CommentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
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


    @GetMapping("/commentList")
    public ResponseResult<?> getCommentList(long articleId, int pageNum, int pageSize) {
        return commentService.getCommentList(articleId, pageNum, pageSize);
    }
}
