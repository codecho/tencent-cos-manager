package com.codecho.tcm.util;

import com.codecho.tcm.param.ObjectParam;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Objects;

/**
 * @desc 上传/下载文件工具类
 * @author codecho
 * @date 2022/8/6 11:38
 * @version 1.0
 */
public class FileUtils {

    /**
     * @desc MultipartFile 转 File
     * @author codecho
     * @date 2022-08-07 16:57:21
     * @param multipartFile MultipartFile对象
     */
    public static File multipartFile2File(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        try (InputStream is = multipartFile.getInputStream();
             OutputStream os = Files.newOutputStream(file.toPath())) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer, 0, 1024)) != -1) {
                os.write(buffer, 0, len);
            }
        }

        return file;
    }

    public static void uploadSmallFile(ObjectParam param, File file) {
        COSClient client = ClientUtils.getClient();
        // 指定文件将要存放的存储桶
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        PutObjectRequest putObjectRequest = new PutObjectRequest(param.getBucket(), param.getKey(), file);
        PutObjectResult putObjectResult = client.putObject(putObjectRequest);
        client.shutdown();
    }

    /**
     *
     * @desc 上传一般大小的文件（不超过20MB）
     * @author codecho
     * @date 2022-08-06 11:42:38
     * @param param 存储对象参数
     */
    public static void uploadSmallFile(ObjectParam param) {
        File localFile = new File(param.getFilePath());
        uploadSmallFile(param, localFile);
    }

    // 使用高级接口上传文件，高级接口封装了简单上传、分块上传接口，根据文件大小智能的选择上传方式，同时支持续传、上传时显示进度等功能。

}
