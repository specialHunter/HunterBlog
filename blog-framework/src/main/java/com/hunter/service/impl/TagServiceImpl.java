package com.hunter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunter.domain.ResponseResult;
import com.hunter.domain.dto.TagDto;
import com.hunter.domain.entity.Tag;
import com.hunter.domain.vo.PageVo;
import com.hunter.domain.vo.TagVo;
import com.hunter.enums.HttpCodeEnum;
import com.hunter.exception.GlobalException;
import com.hunter.mapper.TagMapper;
import com.hunter.service.TagService;
import com.hunter.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 标签Service实现
 *
 * @author Hunter
 * @description 针对表【hunter_tag(标签)】的数据库操作Service实现
 * @createDate 2025-02-10 14:33:47
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
        implements TagService {
    @Override
    public ResponseResult<PageVo> getTagList(Integer pageNum, Integer pageSize, TagDto tagDto) {
        LambdaQueryWrapper<Tag> queryWrapper = Wrappers.<Tag>lambdaQuery()
                .like(Objects.nonNull(tagDto.getName()), Tag::getName, tagDto.getName())
                .like(Objects.nonNull(tagDto.getRemark()), Tag::getRemark, tagDto.getRemark());

        // 分页查询
        Page<Tag> page = new Page<>(pageNum, pageSize);
        List<Tag> tags = page(page, queryWrapper).getRecords();

        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tags, TagVo.class);
        PageVo pageVo = new PageVo(tagVos, page.getTotal());
        return ResponseResult.success(pageVo);
    }

    @Override
    public <T> ResponseResult<T> addTag(Tag tag) {
        if (!StringUtils.hasLength(tag.getName())) {
            throw new GlobalException(HttpCodeEnum.TAG_NAME_CANNOT_EMPTY);
        }
        save(tag);
        return ResponseResult.success();
    }
}




