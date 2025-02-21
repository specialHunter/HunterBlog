package com.hunter.controller;

import com.hunter.domain.ResponseResult;
import com.hunter.domain.dto.TagDto;
import com.hunter.domain.entity.Tag;
import com.hunter.domain.vo.PageVo;
import com.hunter.service.TagService;
import com.hunter.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
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

    /**
     * 获取标签列表
     *
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 标签列表
     */
    @GetMapping("/list")
    public ResponseResult<PageVo> getTagList(Integer pageNum, Integer pageSize, TagDto tagDto) {
        return tagService.getTagList(pageNum, pageSize, tagDto);
    }

    /**
     * 新增标签
     *
     * @param tagDto 标签信息
     * @param <T>    返回类型
     * @return 新增结果
     */
    @PostMapping
    public <T> ResponseResult<T> addTag(@RequestBody TagDto tagDto) {
        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        return tagService.addTag(tag);
    }

    /**
     * 删除标签（逻辑删除）
     *
     * @param id  标签id
     * @param <T> 返回类型
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public <T> ResponseResult<T> deleteTag(@PathVariable("id") Long id) {
        tagService.removeById(id);
        return ResponseResult.success();
    }

    /**
     * 批量删除标签（逻辑删除）
     *
     * @param ids 标签id列表，多个id用逗号分隔
     * @param <T> 返回类型
     * @return 删除结果
     */
    @DeleteMapping
    public <T> ResponseResult<T> deleteTags(String ids) {
        // 单独删除
        if (!ids.contains(",")) {
            tagService.removeById(ids);
        } else {
            List<Long> idList = Arrays.stream(ids.split(","))
                    .map(Long::parseLong)
                    .toList();
            tagService.removeBatchByIds(idList);
        }
        return ResponseResult.success();
    }
}