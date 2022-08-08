package com.codecho.tcm.param;

import lombok.Data;

/**
 * @desc 存储对象参数
 * @author codecho
 * @date 2022/8/7 16:01
 * @version 1.0
 */
@Data
public class ObjectParam {

    /**
     * 存储桶名称
     */
    private String bucket;

    /**
     * 存储桶key，可以认为是文件夹或目录
     */
    private String key;

    /**
     * 本地下载路径
     */
    private String localPath;

    /**
     * 上传文件路径
     */
    private String filePath;

    /**
     * 表示列出的对象的key以prefix开始
     */
    private String prefix;

    /**
     * 分隔符, 设置为/表示列出当前目录下的object, 设置为空表示列出所有的object
     */
    private String delimiter;

    /**
     * 设置最大返回的对象数量，不超过1000
     */
    private int size;

}
