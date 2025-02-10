package com.hunter.controller;

import com.hunter.domain.ResponseResult;
import com.hunter.service.LinkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 友链控制器
 *
 * @author Hunter
 * @since 2025/1/7
 */
@RestController
@RequestMapping("/link")
@Tag(name = "友链模块", description = "友链相关接口")
public class LinkController {

    @Resource
    private LinkService linkService;

    /**
     * 获取所有友链
     *
     * @return 友链列表
     */
    @RequestMapping(value = "/getAllLink", method = RequestMethod.GET)
    public ResponseResult<?> getAllLink() {
        return linkService.getAllLink();
    }

}
