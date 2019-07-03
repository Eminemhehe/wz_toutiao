package com.example.doutiao.service.Imp;

import com.alibaba.fastjson.JSONObject;
import com.example.doutiao.service.QiniuService;
import com.example.doutiao.util.ToutiaoUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by nowcoder on 2016/7/7.
 */
@Service
public class QiniuServiceImp implements QiniuService {
    private static final Logger logger = LoggerFactory.getLogger(QiniuServiceImp.class);
    //设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "5APolJRdnfvOgydu103eJos3Q3OmtgnPjGUnY-UL";
    String SECRET_KEY = "lanYYRxQn_M1mq3YTnMzGVkL-NJpw5PrM_3dmBYF";
    //要上传的空间
    String bucketname = "nowcoder";

    //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //创建上传对象
    Configuration cfg = new Configuration(Zone.zone2());
    UploadManager uploadManager = new UploadManager(cfg);

    private static String QINIU_IMAGE_DOMAIN = "http://7xsetu.com1.z0.glb.clouddn.com/";

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
        String upToken = auth.uploadToken(bucketname);
    //默认不指定key的情况下，以文件内容的hash值作为文件名



    public String saveImage(MultipartFile file) throws IOException {
        try {
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            if (dotPos < 0) {
                return null;
            }
            String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            if (!ToutiaoUtil.isFileAllowed(fileExt)) {
                return null;
            }

            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
            //调用put方法上传
            Response res = uploadManager.put(file.getBytes(), fileName, upToken);
            //打印返回的信息
            if (res.isOK() && res.isJson()) {
                String key = JSONObject.parseObject(res.bodyString()).get("key").toString();
                return ToutiaoUtil.QINUI_DOMAIN + key;
            } else {
                logger.error("七牛异常:" + res.bodyString());
                return null;
            }
        } catch (QiniuException e) {
            // 请求失败时打印的异常的信息
            logger.error("七牛异常:" + e.getMessage());
            return null;
        }
    }
}
