package com.example.doutiao.contorller;


import com.example.doutiao.async.EventModel;
import com.example.doutiao.async.EventProducer;
import com.example.doutiao.async.EventType;
import com.example.doutiao.bean.EntityType;
import com.example.doutiao.bean.HostHolder;
import com.example.doutiao.bean.News;
import com.example.doutiao.service.Imp.LikeServiceImp;
import com.example.doutiao.service.Imp.NewServiceImp;
import com.example.doutiao.util.ToutiaoUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by nowcoder on 2016/7/13.
 */
@Controller
public class LikeController {
    @Autowired
    LikeServiceImp likeService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    NewServiceImp newService;
    @Resource
    EventProducer eventProducer;

    @RequestMapping(path = {"/like"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String like(@Param("newId") int newsId) {
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_NEWS, newsId);
        // 更新喜欢数
        News news = newService.getById(newsId);
        newService.updateLikeCount(newsId, (int) likeCount);
        //异步化处理
        eventProducer.fireEvent(new EventModel(EventType.LIKE)
                .setEntityOwnerId(news.getUserId())
                .setActorId(hostHolder.getUser().getId()).setEntityId(newsId));
        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String dislike(@Param("newId") int newsId) {
        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_NEWS, newsId);
        // 更新喜欢数
        newService.updateLikeCount(newsId, (int) likeCount);
        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
    }
}
