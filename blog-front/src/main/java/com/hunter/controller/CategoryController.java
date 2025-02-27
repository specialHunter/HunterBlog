package com.hunter.controller;

import com.hunter.domain.ResponseResult;
import com.hunter.domain.vo.CategoryVo;
import com.hunter.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类控制器
 *
 * @author Hunter
 * @since 2025/1/2
 */
@RestController
@RequestMapping("/category")
@Tag(name = "分类模块", description = "分类相关接口")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 获取分类列表
     *
     * @return 分类列表
     */
    @RequestMapping(value = "/getCategoryList", method = RequestMethod.GET)
    public ResponseResult<List<CategoryVo>> getCategoryList() {
        return categoryService.getCategoryList();
    }

}
