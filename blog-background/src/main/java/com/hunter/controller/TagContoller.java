package com.hunter.controller;

import com.hunter.domain.ResponseResult;
import com.hunter.domain.dto.TagDto;
import com.hunter.domain.entity.Tag;
import com.hunter.domain.vo.PageVo;
import com.hunter.service.TagService;
import com.hunter.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 获取标签列表
     *
     * @param pageNum 第几页
     * @param pageSize 页大小
     * @return 标签列表
     */
    @GetMapping("/list")
    public ResponseResult<PageVo> getTagList(Integer pageNum, Integer pageSize, TagDto tagDto) {
        return tagService.getTagList(pageNum, pageSize, tagDto);
    }

    @PostMapping()
    public ResponseResult<?> addTag(@RequestBody TagDto tagDto) {
        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        return tagService.addTag(tag);
    }
}