package com.codecho.tcm;

import com.codecho.tcm.param.ObjectParam;
import com.codecho.tcm.service.CosService;
import com.qcloud.cos.model.Bucket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

// @SpringBootTest
@ExtendWith(MockitoExtension.class)
class ManagerApplicationTests {

    @InjectMocks
    private CosService cosService;

    @Test
    void testCreateBucket() {
        Bucket bucket = cosService.createBucket("test-1256052415");
        System.out.println(bucket);
    }

    @Test
    void testDeleteBucket() {
        cosService.deleteBucket("test-1256052415");
    }

    @Test
    void testListBuckets() {
        List<Bucket> buckets = cosService.listBuckets();
        System.out.println(buckets);
    }

    @Test
    void testDownloadObject() {
        ObjectParam param = new ObjectParam();
        param.setBucket("myblog-1256052415");
        param.setKey("Centos安装Redis/使用RedisTemplate操作Redis.png");
        param.setLocalPath("D:\\tencent_cos\\使用RedisTemplate操作Redis.png");
        cosService.downloadObject(param);
    }

}
