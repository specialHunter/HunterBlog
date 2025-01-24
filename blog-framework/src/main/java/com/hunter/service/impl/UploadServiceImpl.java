package com.hunter.service.impl;

import com.google.gson.Gson;
import com.hunter.domain.ResponseResult;
import com.hunter.enums.HttpCodeEnum;
import com.hunter.exception.GlobalException;
import com.hunter.service.UploadService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author Hunter
 * @since 2025/1/24
 */
@Service
@Slf4j
public class UploadServiceImpl implements UploadService {
    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    /**
     * 域名
     */
    @Value("${qiniu.domain}")
    private String domain;

    @Override
    public ResponseResult<String> uploadImg(MultipartFile img) throws IOException {
        // 判断文件类型
        String originalFilename = img.getOriginalFilename();
        assert originalFilename != null;

        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));

        if (!".jpg".equals(fileType) && !".jpeg".equals(fileType)
                && !".png".equals(fileType)) {
            throw new GlobalException(HttpCodeEnum.FILE_TYPE_ERROR);
        }

        // “当前的 年份/月份/日期 + 随机数 + 文件类型”作为文件名
        String fileName =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd/"))
                        + UUID.randomUUID()
                        + fileType;

        // 上传文件到七牛云，返回链接地址
        String url = uploadToQiniu(img, fileName);
        return ResponseResult.success(url);
    }

    /**
     * 上传文件到七牛云
     *
     * @param img      上传的文件
     * @param fileName 文件名
     * @return 文件存储的地址
     */
    private String uploadToQiniu(MultipartFile img, String fileName) throws IOException {
        // 构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        // 指定分片上传版本
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            UploadManager uploadManager = new UploadManager(cfg);
            InputStream inputStream = img.getInputStream();
            // 默认不指定key的情况下，以文件内容的hash值作为文件名
            Response response = uploadManager.put(inputStream, fileName, upToken, null, null);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("文件成功上传到七牛云，key: {}, hash: {}", putRet.key, putRet.hash);
            return domain + putRet.key;
        } catch (QiniuException ex) {
            log.error("上传文件到七牛云失败: {}", ex.getMessage(), ex);
            throw ex;
        } catch (FileNotFoundException ex) {
            log.error("文件不存在: {}", ex.getMessage(), ex);
            throw ex;
        }
    }
}
