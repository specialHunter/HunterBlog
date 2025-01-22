package com.hunter.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论的视图对象
 * @author Hunter
 * @since 2025/1/21
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // 序列化时，null值不参与序列化
public class CommentVo {
    /**
     * 评论id
     */
    private Long id;

    /**
     * 文章id
     */
    private int articleId;

    /**
     * 子评论列表
     */
    private List<CommentVo> children;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 发表评论的用户id
     */
    private Long createBy;

    /**
     * 发表评论的昵称，注意不是用户名
     */
    private String username;

    /**
     * 发表评论的时间
     */
    private LocalDateTime createTime;

    /**
     * 根评论id
     */
    private Long rootId;

    /**
     * 所回复的目标评论的userid
     */
    private Long toCommentUserId;

    /**
     * 所回复的目标评论的用户名
     */
    private String toCommentUsername;

    /**
     * 回复目标评论id
     */
    private Long toCommentId;
}
