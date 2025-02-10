package com.hunter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunter.domain.entity.Tag;
import com.hunter.service.TagService;
import com.hunter.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
 * @author Hunter
 * @description 针对表【hunter_tag(标签)】的数据库操作Service实现
 * @createDate 2025-02-10 14:33:47
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
        implements TagService {
}




