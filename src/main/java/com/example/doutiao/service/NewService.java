package com.example.doutiao.service;

import com.example.doutiao.dao.NewsDAO;
import com.example.doutiao.model.News;
import com.example.doutiao.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

/**
 * Created by wz on 2019/5/13.
 */
@Service
public class NewService {
    @Autowired//@Resource
    private NewsDAO newsDAO;
    public List<News> getLastestNews(int userID,int offset,int limit){
        return newsDAO.selectByUserIdAndOffset(userID,offset,limit);
    }
    public int addNews(News news) {
        newsDAO.addNews(news);
        return news.getId();
    }
    public int updateLikeCount(int id, int count) {
        return newsDAO.updateLikeCount(id, count);
    }

    public News getById(int newsId) {
        return newsDAO.getById(newsId);
    }
    public int updateCommentCount(int id, int count) {
        return newsDAO.updateCommentCount(id, count);
    }

    public  String savaImage(MultipartFile file)throws IOException {
        int dotPos = file.getOriginalFilename().lastIndexOf(".");
        if(dotPos<0){
            return null;
        }
        //获取文件扩展名
        String fileExt = file.getOriginalFilename().substring(dotPos+1).toLowerCase();
        if(!ToutiaoUtil.isFileAllowed(fileExt)){
                return  null;
        }
        String filename = UUID.randomUUID().toString().replaceAll("-","")+"."+fileExt;
        Files.copy(file.getInputStream(),new File(ToutiaoUtil.IMAGE_DIR+filename).toPath());
        return ToutiaoUtil.TOUTIAO_DOMAIN+"image?name="+filename;

    }
}
