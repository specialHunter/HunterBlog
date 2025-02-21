package com.hunter.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 *
 * @author Hunter
 * @since 2025/2/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVo {

    List<MenuVo> menus;
}
