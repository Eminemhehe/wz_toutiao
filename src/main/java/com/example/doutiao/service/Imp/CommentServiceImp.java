package com.example.doutiao.service.Imp;

import com.example.doutiao.mapper.CommentDAO;
import com.example.doutiao.bean.Comment;
import com.example.doutiao.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by nowcoder on 2016/7/7.
 */
@Service
public class CommentServiceImp implements CommentService {
    private static final Logger logger = LoggerFactory.getLogger(QiniuServiceImp.class);

    @Resource
    CommentDAO commentDAO;

    public List<Comment> getCommentsByEntity(int entityId, int entityType) {
        return commentDAO.selectByEntity(entityId, entityType);
    }

    public int addComment(Comment comment) {
        return commentDAO.addComment(comment);
    }

    public int getCommentCount(int entityId, int entityType) {
        return commentDAO.getCommentCount(entityId, entityType);
    }
}
