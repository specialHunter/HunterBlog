package com.hunter.service;

import com.hunter.domain.ResponseResult;
import com.hunter.domain.dto.TagDto;
import com.hunter.domain.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hunter.domain.vo.PageVo;

/**
 * @author Hunter
 * @description 针对表【hunter_tag(标签)】的数据库操作Service
 * @createDate 2025-02-10 14:33:47
 */
public interface TagService extends IService<Tag> {

    /**
     * 获取标签列表
     *
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @param tagDto   条件查询
     * @return 标签列表
     */
    ResponseResult<PageVo> getTagList(Integer pageNum, Integer pageSize, TagDto tagDto);

    /**
     * 添加标签
     *
     * @param tag 标签
     * @param <T> 返回类型
     * @return 添加结果
     */
    <T> ResponseResult<T> addTag(Tag tag);
}
