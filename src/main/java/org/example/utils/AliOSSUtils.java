package org.example.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Component
public class AliOSSUtils {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    //实现上传图片到OSS
    public String upload(MultipartFile file) throws IOException {
        //获取上传的文件的输入流
        InputStream inputStream = file.getInputStream();
        //避免文件覆盖
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        //上传文件到OSS
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileName, inputStream);
        //文件访问路径
        String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + fileName;
        //关闭ossClient
        ossClient.shutdown();
        //把上传到OSS的路径返回
        return url;
    }

    // 实现删除OSS上的文件
    public void delete(String url) {
        // 从文件URL中提取文件名
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        // 创建OSS客户端
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 删除文件
        ossClient.deleteObject(bucketName, fileName);
        // 关闭OSS客户端
        ossClient.shutdown();
    }
}
