package com.example.doutiao.service;

import com.example.doutiao.bean.News;
import com.example.doutiao.util.ToutiaoUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

/**
 * Created by wz on 2019/7/3.
 */
public interface NewService {
    List<News> getLastestNews(int userID, int offset, int limit);

    int addNews(News news);

    int updateLikeCount(int id, int count);

    News getById(int newsId);

    int updateCommentCount(int id, int count);

    String savaImage(MultipartFile file) throws IOException;
}
