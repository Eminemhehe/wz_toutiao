package com.example.doutiao.contorller;

import com.example.doutiao.model.EntityType;
import com.example.doutiao.model.HostHolder;
import com.example.doutiao.model.ViewObject;
import com.example.doutiao.model.News;
import com.example.doutiao.service.LikeService;
import com.example.doutiao.service.NewService;
import com.example.doutiao.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wz on 2019/5/13.
 */
@Controller
public class HomeController {
    @Resource
    NewService newService;
    @Resource
    UserService userService;
    @Resource
    HostHolder hostHolder;
    @Resource
    LikeService likeService;

    private List<ViewObject> getNews (int userId,int offset, int limit){
        List<News> newsList =newService.getLastestNews(userId,offset,limit);
        int localUserId =hostHolder.getUser()!=null?hostHolder.getUser().getId():0;
        List<ViewObject> vos =  new ArrayList<>();
        for(News news :newsList){
            ViewObject vo = new ViewObject();
            vo.set("news",news);
            vo.set("user",userService.getUser(news.getUserId()));
            if (localUserId != 0) {
                vo.set("like", likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS, news.getId()));
            } else {
                vo.set("like", 0);
            }
            vos.add(vo);
        }
        return vos;
    }

    @RequestMapping(path = {"/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model,@RequestParam(value = "pop",defaultValue = "0")int pop) {
        model.addAttribute("vos",getNews(0,0,10));
        model.addAttribute("pop",pop);
       return  "home";
    }
    @RequestMapping(path = {"/user/{userId}"},method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId")int userId) {
        model.addAttribute("vos",getNews(userId,0,10));
        return  "home";
    }
}
