package com.hunter.service;

import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Hunter
* @description 针对表【hunter_link(友链)】的数据库操作Service
* @createDate 2025-01-07 21:01:11
*/
public interface LinkService extends IService<Link> {

    /**
     * 获取所有友链列表
     *
     * @return 友链列表
     */
    ResponseResult<?> getAllLink();
}
