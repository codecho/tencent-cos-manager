package com.codecho.tcm.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;

/**
 * @desc 客户端工具类
 * @author codecho
 * @version 1.0
 * @date 2022/8/6 10:45
 */
public class ClientUtils {

    // 腾讯云api SecretId，访问https://console.cloud.tencent.com/cam/capi获取
    private static final String SECRET_ID = "SecretId";

    // 腾讯云api SecretKey
    private static final String SECRET_KEY = "SecretKey";

    // 存储桶的区域简称，详见https://cloud.tencent.com/document/product/436/6224
    private static final String REGION = "ap-shanghai";

    /**
     * @desc 创建 cos client
     * @author codecho
     * @date 2022-08-06 11:13:35
     */
    public static COSClient getClient() {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials(SECRET_ID, SECRET_KEY);
        // 2 设置 bucket 的地域
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(REGION);
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }

}
