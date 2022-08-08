package com.codecho.tcm.controller;

import com.codecho.tcm.param.ObjectParam;
import com.codecho.tcm.service.CosService;
import com.codecho.tcm.util.FileUtils;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.COSObjectSummary;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc cos controller
 * @author codecho
 * @date 2022/8/7 15:17
 * @version 1.0
 */
@RestController
@RequestMapping("/cos")
public class CosController {

    @Resource
    private CosService cosService;

    @PostMapping(value = "/bucket/create/{bucket}")
    public Map<String, Object> createBucket(@PathVariable String bucket) {
        Bucket data = cosService.createBucket(bucket);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", data);
        return response;
    }

    @PostMapping(value = "/bucket/delete/{bucket}")
    public Map<String, Object> deleteBucket(@PathVariable String bucket) {
        cosService.deleteBucket(bucket);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }

    @PostMapping("/bucket/list")
    public Map<String, Object> listBuckets() {
        List<Bucket> buckets = cosService.listBuckets();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", buckets);
        return response;
    }

    @PostMapping("/bucket/get/{bucket}")
    public Map<String, Object> getBucket(@PathVariable String bucket) {
        Bucket data = cosService.getBucketByName(bucket);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", data);
        return response;
    }

    @PostMapping("/object/list")
    public Map<String, Object> listObjects(@RequestBody ObjectParam param) {
        List<COSObjectSummary> summaries = cosService.listObjects(param);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", summaries);
        return response;
    }

    @PostMapping("/object/download")
    public Map<String, Object> downloadObject(@RequestBody ObjectParam param) {
        cosService.downloadObject(param);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }

    @PostMapping("/object/delete")
    public Map<String, Object> deleteObject(@RequestBody ObjectParam param) {
        cosService.deleteObject(param);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }

    @PostMapping("/uploadLocalSmallFile")
    public Map<String, Object> uploadLocalSmallFile(@RequestBody ObjectParam param) {
        cosService.uploadSmallFile(param);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }

    @PostMapping("/uploadSmallFile")
    public Map<String, Object> uploadSmallFile(@RequestBody ObjectParam param, @RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        try {
            cosService.uploadSmallFile(param, FileUtils.multipartFile2File(file));
            response.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("errMsg", e.getMessage());
        }

        return response;
    }
}
