package com.hunter.controller;

import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.Tag;
import com.hunter.service.TagService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 标签控制器
 *
 * @author Hunter
 * @since 2025/2/10
 */
@RestController
@RequestMapping("/content/tag")
public class TagContoller {
    @Resource
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<List<Tag>> getTagList() {
        List<Tag> tagList = tagService.list();
        return ResponseResult.success(tagList);
    }
}