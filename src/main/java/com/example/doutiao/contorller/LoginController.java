package com.example.doutiao.contorller;

import com.example.doutiao.aspect.LogAspect;
import com.example.doutiao.async.EventModel;
import com.example.doutiao.async.EventProducer;
import com.example.doutiao.async.EventType;
import com.example.doutiao.model.HostHolder;
import com.example.doutiao.service.UserService;
import com.example.doutiao.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by wz on 2019/5/13.
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);


    @Resource
    UserService userService;
    @Resource
    EventProducer eventProducer;



    @RequestMapping(path = {"/reg/"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String reg(Model model, @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "rember", defaultValue = "0") int rememberme,
                      HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.register(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme > 0) {
                    cookie.setMaxAge(3600 * 24 * 5);
                }
                response.addCookie(cookie);
                return ToutiaoUtil.getJSONString(0, "reg ok");
            } else {
                return ToutiaoUtil.getJSONString(1, map);
            }

        } catch (Exception e) {
            logger.error("reg not ok" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "注册失败");
        }
    }

    @RequestMapping(path = {"/login/"}, produces = "application/json; charset=utf-8", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String login(Model model, @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "rember", defaultValue = "0") int rememberme,
                        HttpServletResponse response) {

        try {
            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme > 0) {
                    cookie.setMaxAge(3600 * 24 * 5);
                }
                response.addCookie(cookie);
               eventProducer.fireEvent(new EventModel(EventType.LOGIN)
                        .setActorId(userService.getIdByneme(username))
                       .setExt("username", username).setExt("email", "519345786@qq.com"));
                return ToutiaoUtil.getJSONString(0, "log ok ");
            } else {
                return ToutiaoUtil.getJSONString(1, map);
            }

        } catch (Exception e) {
            logger.error("log not ok" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "log not ok");
        }
    }


    @RequestMapping(path = {"/logout/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {
        //设置ticket过期无效
        userService.logout(ticket);
        //登出后回到首页
        return "redirect:/";
    }

}


