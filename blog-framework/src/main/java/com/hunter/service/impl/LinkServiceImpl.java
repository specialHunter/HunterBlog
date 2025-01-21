package com.hunter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunter.constants.SystemConstants;
import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.Link;
import com.hunter.domain.vo.LinkVo;
import com.hunter.mapper.LinkMapper;
import com.hunter.service.LinkService;
import com.hunter.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Hunter
* @description 针对表【hunter_link(友链)】的数据库操作Service实现
* @createDate 2025-01-07 21:01:11
*/
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
    implements LinkService{

    @Override
    public ResponseResult<?> getAllLink() {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_APPROVED);

        List<Link> linkList = list(queryWrapper);

        List<LinkVo> linkVoList = BeanCopyUtils.copyBeanList(linkList, LinkVo.class);
        return ResponseResult.success(linkVoList);
    }
}




