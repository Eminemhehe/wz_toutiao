package com.example.doutiao.service;

import com.example.doutiao.util.RedisKeyUtil;

/**
 * Created by wz on 2019/7/3.
 */
public interface LikeService {


    /**
     * 如果喜欢返回1，不喜欢-1，否则0
     *
     * @Param userId
     * @Param entityType
     * @Param entityId
     */
    int getLikeStatus(int userId, int entityType, int entityId);

    long like(int userId, int entityType, int entityId);

    long disLike(int userId, int entityType, int entityId);
}
