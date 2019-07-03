package com.example.doutiao.interceptor;

import com.example.doutiao.mapper.LoginTicketDAO;
import com.example.doutiao.mapper.UserDAO;
import com.example.doutiao.bean.HostHolder;
import com.example.doutiao.bean.LoginTicket;
import com.example.doutiao.bean.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by wz on 2019/5/15.
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {
    @Resource
    private LoginTicketDAO loginTicketDAO;
    @Resource
    private UserDAO userDAO;
    @Resource
    private HostHolder hostHolder;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        if(httpServletRequest.getCookies()!=null){
            for(Cookie cookie :httpServletRequest.getCookies()){
                if(cookie.getName().equals("ticket")){
                    ticket = cookie.getValue();
                    break;
                }

            }
        }
        if(ticket!=null){
            LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
            if(loginTicket==null||loginTicket.getExpired().before(new Date())||loginTicket.getStatus()!=0){
                return  true;
            }

            User user = userDAO.selectById(loginTicket.getUserId());
            hostHolder.setUser(user);
        }
        return  true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
            if(modelAndView!=null && hostHolder.getUser()!=null){
                modelAndView.addObject("user",hostHolder.getUser());
            }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
            hostHolder.clear();
    }
}
