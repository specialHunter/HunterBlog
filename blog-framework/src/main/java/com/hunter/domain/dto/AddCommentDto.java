package com.hunter.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 添加评论 数据传输对象
 * 使用数据传输对象（DTO）能够使前端只传递必要的参数，简化接口设计，提高可读性。
 *
 * @author Hunter
 * @since 2025/2/8
 */
@Data
@Schema(name = "添加评论DTO", description = "添加评论的请求体")
public class AddCommentDto {

    /**
     * 评论类型（0代表文章评论，1代表友链评论）
     */
    @Schema(description = "评论类型（0代表文章评论，1代表友链评论）", example = "0")
    private String type;

    /**
     * 文章id
     */
    @Schema(description = "文章id", example = "1")
    private Long articleId;

    /**
     * 根评论id
     */
    @Schema(description = "根评论id", example = "-1")
    private Long rootId;

    /**
     * 评论内容
     */
    @Schema(description = "评论内容", example = "这是一条评论")
    private String content;

    /**
     * 所回复的目标评论的user id
     */
    @Schema(description = "所回复的目标评论的user id", example = "-1")
    private Long toCommentUserId;

    /**
     * 回复目标评论id
     */
    @Schema(description = "回复目标评论id", example = "-1")
    private Long toCommentId;
}
