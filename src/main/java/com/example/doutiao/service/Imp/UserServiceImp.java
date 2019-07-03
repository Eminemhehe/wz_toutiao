package com.example.doutiao.service.Imp;




import com.example.doutiao.bean.LoginTicket;
import com.example.doutiao.bean.User;
import com.example.doutiao.mapper.LoginTicketDAO;
import com.example.doutiao.mapper.UserDAO;
import com.example.doutiao.service.UserService;
import com.example.doutiao.util.ToutiaoUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.*;


/**
 * Created by wz on 2019/5/13.
 */
@Service
public class UserServiceImp implements UserService {
    @Resource
    private UserDAO userDAO;

    @Resource
    private LoginTicketDAO  loginTicketDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    public int  getIdByneme(String name){
        return  userDAO.getIdByname(name);
    }

    public Map<String, Object> register(String username, String password) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(username)) {
            map.put("masgname", "username is not null");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("masgpassword", "password is not null");
            return map;
        }
        User user = userDAO.selectByName(username);
        if (user != null) {
//            String s1 = "用户名已经被注册";
//            String s2 = new String(s1.getBytes("ISO-8859-1"),"GBK");
            map.put("msgname", "用户名已经被注册");
            System.out.println(map.get("msgname"));
            return map;
        }

        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setPassword(ToutiaoUtil.MD5(password + user.getSalt()));
        userDAO.addUser(user);
        //登陆
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }


    public String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }


    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(username)) {
            map.put("masgname", "username is not null");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("masgpassword", "password is not null");
            return map;
        }

        User user = userDAO.selectByName(username);
        if (user == null) {
            map.put("msgname", "username is not exist");
            return map;
        }

        if (!ToutiaoUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msgps", "password is not true");
            return map;
        }
        //ticket下发
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;

    }

    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket, 1);

    }
}
