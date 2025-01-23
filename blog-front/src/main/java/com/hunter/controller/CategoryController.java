package com.hunter.controller;

import com.hunter.domain.ResponseResult;
import com.hunter.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分类控制器
 *
 * @author Hunter
 * @since 2025/1/2
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 获取分类列表
     *
     * @return 分类列表
     */
    @RequestMapping(value = "/getCategoryList", method = RequestMethod.GET)
    public ResponseResult<?> getCategoryList() {
        return categoryService.getCategoryList();
    }

}
