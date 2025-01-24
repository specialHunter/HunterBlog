package com.hunter.service;

import com.hunter.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 上传服务接口
 *
 * @author Hunter
 * @since 2025/1/24
 */
public interface UploadService {
    /**
     * 上传图片
     * @param img 图片文件
     * @return 上传结果
     */
    ResponseResult<String> uploadImg(MultipartFile img) throws IOException;
}
