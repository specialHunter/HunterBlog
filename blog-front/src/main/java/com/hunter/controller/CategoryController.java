package com.hunter.controller;

import com.hunter.domain.ResponseResult;
import com.hunter.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hunter
 * @since 2025/1/2
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @RequestMapping(value = "/getCategoryList", method = RequestMethod.GET)
    public ResponseResult<?> getCategoryList() {
        return categoryService.getCategoryList();
    }

}
