package com.codecho.tcm.util;

import com.codecho.tcm.param.ObjectParam;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc 存储桶工具类
 * @author codecho
 * @date 2022/8/6 11:18
 * @version 1.0
 */
@Slf4j
public class BucketUtils {

    /**
     * @desc 创建存储桶
     * @author codecho
     * @date 2022-08-06 11:23:01
     * @param bucket 存储桶名称(格式：name-APPID)
     * @param access bucket权限(Private(私有读写)、其他可选有 PublicRead（公有读私有写）、PublicReadWrite（公有读写）)
     */
    public static Bucket createBucket(String bucket, CannedAccessControlList access) {
        // 获取cos client
        COSClient client = ClientUtils.getClient();

        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucket);
        createBucketRequest.setCannedAcl(access);
        Bucket bucketResult;
        try{
            bucketResult = client.createBucket(createBucketRequest);
        } catch (CosClientException serverException) {
            log.error("创建存储桶失败: {}", serverException.getMessage());
            throw serverException;
        } finally {
            client.shutdown();
        }

        return bucketResult;
    }

    /**
     * @desc 删除存储桶
     * @author codecho
     * @date 2022-08-06 12:38:14
     * @param bucket 存储桶名称
     */
    public static void deleteBucket(String bucket) {
        COSClient client = ClientUtils.getClient();
        client.deleteBucket(bucket);
        client.shutdown();
    }

    /**
     * @desc 获取所有存储桶
     * @author codecho
     * @date 2022-08-06 11:33:17
     */
    public static List<Bucket> listBuckets() {
        COSClient client = ClientUtils.getClient();
        List<Bucket> buckets = client.listBuckets();
        client.shutdown();
        return buckets;
    }

    /**
     * @desc 根据名称获取存储桶
     * @author codecho
     * @date 2022-08-07 15:07:52
     * @param name 桶名称
     */
    public static Bucket getBucketByName(String name) {
        COSClient client = ClientUtils.getClient();
        List<Bucket> buckets = client.listBuckets();
        client.shutdown();
        for (Bucket bucket : buckets) {
            if (bucket.getName().equals(name)) {
                return bucket;
            }
        }
        return null;
    }

    /**
     * @desc 查询存储桶下的所有对象列表
     * @author codecho
     * @date 2022-08-06 12:03:44
     * @param param 存储对象参数
     */
    public static List<COSObjectSummary> listObjects(ObjectParam param) {
        COSClient client = ClientUtils.getClient();

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(param.getBucket());
        // prefix表示列出的object的key以prefix开始
        // listObjectsRequest.setPrefix("images/");
        listObjectsRequest.setPrefix(param.getPrefix());
        // deliter表示分隔符, 设置为/表示列出当前目录下的object, 设置为空表示列出所有的object
        // listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setDelimiter(param.getDelimiter());
        // 设置最大遍历出多少个对象, 一次listobject最大支持1000
        listObjectsRequest.setMaxKeys(Math.min(param.getSize(), 1000));
        ObjectListing objectListing = null;
        List<COSObjectSummary> objectList = new ArrayList<>();
        do {
            try {
                objectListing = client.listObjects(listObjectsRequest);
            } catch (CosClientException e) {
                e.printStackTrace();
                log.error("获取对象列表失败: {}", e.getMessage());
                return new ArrayList<>();
            }

            // object summary表示所有列出的object列表
            List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
            if (!CollectionUtils.isEmpty(cosObjectSummaries)) {
                objectList.addAll(cosObjectSummaries);
            }

            // 获取下次遍历的游标
            String nextMarker = objectListing.getNextMarker();
            listObjectsRequest.setMarker(nextMarker);
        } while (objectListing.isTruncated());

        client.shutdown();
        return objectList;
    }

    /**
     * @desc 将文件下载到本地目录
     * @author codecho
     * @date 2022-08-06 12:19:30
     * @param param 存储对象参数
     */
    public static void downloadObject(ObjectParam param) {
        COSClient client = ClientUtils.getClient();
        GetObjectRequest request = new GetObjectRequest(param.getBucket(), param.getKey());
        client.getObject(request, new File(param.getLocalPath()));
        client.shutdown();
    }

    /**
     * @desc 删除指定目录的对象
     * @author codecho
     * @date 2022-08-06 12:23:41
     * @param param 存储对象参数
     */
    public static void deleteObject(ObjectParam param) {
        COSClient client = ClientUtils.getClient();
        client.deleteObject(param.getBucket(), param.getKey());
        client.shutdown();
    }
}
