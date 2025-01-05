package com.hunter.service;

import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Hunter
* @description 针对表【hunter_category(分类表)】的数据库操作Service
* @createDate 2025-01-02 22:20:53
*/
public interface CategoryService extends IService<Category> {

    ResponseResult<?> getCategoryList();
}
