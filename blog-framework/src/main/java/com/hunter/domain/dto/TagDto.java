package com.hunter.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 查询标签列表 数据传输对象
 * 让前端传递查询条件
 *
 * @author Hunter
 * @since 2025/2/21
 */
@Data
@Schema(name = "标签列表DTO", description = "查询标签列表的请求体")
public class TagDto {
    /**
     * 标签名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;
}
