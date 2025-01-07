package com.hunter.domain.vo;

import lombok.Data;

/**
 * @author Hunter
 * @since 2025/1/7
 */
@Data
public class LinkVo {
    /**
     * 友链id
     */
    private Long id;

    /**
     * 网站名称
     */
    private String name;

    /**
     * 网站logo
     */
    private String logo;

    /**
     * 网站描述
     */
    private String description;

    /**
     * 网站地址
     */
    private String address;
}
