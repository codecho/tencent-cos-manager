package com.codecho.tcm.service;

import com.codecho.tcm.param.ObjectParam;
import com.codecho.tcm.util.BucketUtils;
import com.codecho.tcm.util.FileUtils;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.COSObjectSummary;
import com.qcloud.cos.model.CannedAccessControlList;
import org.springframework.stereotype.Service;

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

    public Bucket createBucket(String bucket) {
        return BucketUtils.createBucket(bucket, CannedAccessControlList.PublicRead);
    }

    public void deleteBucket(String bucket) {
        BucketUtils.deleteBucket(bucket);
    }

    public List<Bucket> listBuckets() {
        return BucketUtils.listBuckets();
    }

    public Bucket getBucketByName(String name) {
        return BucketUtils.getBucketByName(name);
    }

    public List<COSObjectSummary> listObjects(ObjectParam param) {
        return BucketUtils.listObjects(param);
    }

    public void downloadObject(ObjectParam param) {
        BucketUtils.downloadObject(param);
    }

    public void deleteObject(ObjectParam param) {
        BucketUtils.deleteObject(param);
    }

    public void uploadSmallFile(ObjectParam param) {
        FileUtils.uploadSmallFile(param);
    }

    public void uploadSmallFile(ObjectParam param, File file) {
        FileUtils.uploadSmallFile(param, file);
    }
}
