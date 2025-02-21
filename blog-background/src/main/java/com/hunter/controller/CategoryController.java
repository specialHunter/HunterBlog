package com.hunter.controller;

import com.hunter.domain.ResponseResult;
import com.hunter.domain.vo.CategoryVo;
import com.hunter.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Hunter
 * @since 2025/2/21
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /**
     * 获取所有分类列表
     *
     * @return 分类列表
     */
    @GetMapping("/listAllCategory")
    public ResponseResult<List<CategoryVo>> listAllCategory() {
        return categoryService.getCategoryList();
    }
}
