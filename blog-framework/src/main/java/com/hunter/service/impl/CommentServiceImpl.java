package com.hunter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.Comment;
import com.hunter.domain.vo.CommentVo;
import com.hunter.domain.vo.PageVo;
import com.hunter.enums.HttpCodeEnum;
import com.hunter.exception.GlobalException;
import com.hunter.mapper.CommentMapper;
import com.hunter.service.CommentService;
import com.hunter.service.UserService;
import com.hunter.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hunter
 * @description 针对表【hunter_comment(评论表)】的数据库操作Service实现
 * @createDate 2025-01-21 17:11:08
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
        implements CommentService {

    @Resource
    private UserService userService;

    @Override
    public ResponseResult<?> getCommentList(long articleId, int pageNum, int pageSize) {
        // 查询指定文章id对应的评论
        LambdaQueryWrapper<Comment> queryWrapper = Wrappers.<Comment>lambdaQuery()
                .eq(Comment::getArticleId, articleId)
                .eq(Comment::getRootId, -1) // 根评论
                .orderByAsc(Comment::getCreateTime);

        // 分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        List<Comment> commentList = page(page, queryWrapper).getRecords();

        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(commentList, CommentVo.class);
        commentVos = commentVos.stream()
                .peek(commentVo -> {
                            setNickNameAndToCommentUsername(commentVo);
                            setChildrenComments(commentVo);
                        }
                )
                .collect(Collectors.toList());

        PageVo pageVo = new PageVo(commentVos, page.getTotal());
        return ResponseResult.success(pageVo);
    }

    @Override
    public ResponseResult<?> addComment(Comment comment) {
        // 评论内容不能为空
        if (!StringUtils.hasLength(comment.getContent())) {
            throw new GlobalException(HttpCodeEnum.CONTENT_CANNOT_EMPTY);
        }
        save(comment);
        return ResponseResult.success();
    }

    /**
     * 设置评论的子评论 【未继续递归子评论】
     * 如果评论的 根评论的id = 目标评论的id，则为目标评论的子评论
     *
     * @param commentVo 目标评论vo
     */
    private void setChildrenComments(CommentVo commentVo) {
        List<Comment> children = list(Wrappers.<Comment>lambdaQuery()
                .eq(Comment::getRootId, commentVo.getId())
                .orderByAsc(Comment::getCreateTime));
        List<CommentVo> childrenCommentVos = BeanCopyUtils.copyBeanList(children, CommentVo.class);
        childrenCommentVos = childrenCommentVos.stream()
                .peek(this::setNickNameAndToCommentUsername)
                .collect(Collectors.toList());
        commentVo.setChildren(childrenCommentVos);
    }

    /**
     * 设置 评论的用户昵称 和 被评论的用户昵称
     *
     * @param commentVo 评论vo
     */
    private void setNickNameAndToCommentUsername(CommentVo commentVo) {
        // 设置评论的用户昵称
        commentVo.setUsername(
                userService.getById(commentVo.getCreateBy()).getNickName());
        // 被评论的用户id不为-1，表示回复了对应用户id的评论，需要设置被评论的用户昵称
        if (commentVo.getToCommentUserId() != -1) {
            commentVo.setToCommentUsername(
                    userService.getById(commentVo.getToCommentUserId()).getNickName());
        }
    }
}




