package com.example.doutiao.service;

import com.example.doutiao.bean.Comment;
import com.example.doutiao.mapper.CommentDAO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wz on 2019/7/3.
 */
public interface CommentService {

    List<Comment> getCommentsByEntity(int entityId, int entityType);

    int addComment(Comment comment);

    int getCommentCount(int entityId, int entityType);
}
