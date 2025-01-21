package com.hunter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunter.domain.entity.Comment;
import com.hunter.service.CommentService;
import com.hunter.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author Hunter
* @description 针对表【hunter_comment(评论表)】的数据库操作Service实现
* @createDate 2025-01-21 17:11:08
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

}




