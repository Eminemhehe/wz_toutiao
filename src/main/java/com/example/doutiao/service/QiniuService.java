package com.example.doutiao.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * Created by wz on 2019/7/3.
 */
public interface QiniuService {

     String saveImage(MultipartFile file) throws IOException;
}
