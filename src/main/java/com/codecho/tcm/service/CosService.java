package com.codecho.tcm.service;

import com.codecho.tcm.param.ObjectParam;
import com.codecho.tcm.util.BucketUtils;
import com.codecho.tcm.util.FileUtils;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.COSObjectSummary;
import com.qcloud.cos.model.CannedAccessControlList;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * @author codecho
 * @version 1.0
 * @desc cos service
 * @date 2022/8/6 10:44
 */
@Service
public class CosService {

    @Resource
    private BucketUtils bucketUtils;

    @Resource
    private FileUtils fileUtils;

    public Bucket createBucket(String bucket) {
        return bucketUtils.createBucket(bucket, CannedAccessControlList.PublicRead);
    }

    public void deleteBucket(String bucket) {
        bucketUtils.deleteBucket(bucket);
    }

    public List<Bucket> listBuckets() {
        return bucketUtils.listBuckets();
    }

    public Bucket getBucketByName(String name) {
        return bucketUtils.getBucketByName(name);
    }

    public List<COSObjectSummary> listObjects(ObjectParam param) {
        return bucketUtils.listObjects(param);
    }

    public void downloadObject(ObjectParam param) {
        bucketUtils.downloadObject(param);
    }

    public void deleteObject(ObjectParam param) {
        bucketUtils.deleteObject(param);
    }

    public void uploadSmallFile(ObjectParam param) {
        fileUtils.uploadSmallFile(param);
    }

    public void uploadSmallFile(ObjectParam param, File file) {
        fileUtils.uploadSmallFile(param, file);
    }
}
