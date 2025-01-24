package com.hunter.controller;

import com.hunter.domain.ResponseResult;
import com.hunter.service.UploadService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 图片上传控制器
 *
 * @author Hunter
 * @since 2025/1/23
 */
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Resource
    private UploadService uploadService;

    /**
     * 上传图片
     * @param img 图片文件
     * @return 上传结果
     */
    @PostMapping
    public ResponseResult<String> uploadImg(MultipartFile img) throws IOException {
        return uploadService.uploadImg(img);
    }
}
