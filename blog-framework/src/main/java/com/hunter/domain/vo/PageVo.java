package com.hunter.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Hunter
 * @since 2025/1/5
 */
@Data
@AllArgsConstructor
public class PageVo {
    /**
     * 每页包含的行列表
     */
    private List<?> rows;

    /**
     * 总行数
     */
    private Long total;
}
