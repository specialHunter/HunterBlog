package com.hunter.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 标签
 *
 * @TableName hunter_tag
 */
@TableName(value = "hunter_tag")
@Data
public class Tag {
    /**
     * 标签 id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标签名
     */
    private String name;

    /**
     *
     */
    private Long createBy;

    /**
     *
     */
    private LocalDateTime createTime;

    /**
     *
     */
    private Long updateBy;

    /**
     *
     */
    private LocalDateTime updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;

    /**
     * 备注
     */
    private String remark;
}