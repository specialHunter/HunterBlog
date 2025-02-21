package com.hunter.domain.vo;

import lombok.Data;

/**
 * @author Hunter
 * @since 2025/2/21
 */
@Data
public class TagVo {
    /**
     * 标签 id
     */
    private Long id;

    /**
     * 标签名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;
}
