package com.example.doutiao.service;

import com.example.doutiao.bean.User;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by wz on 2019/7/3.
 */
public interface UserService {

    User getUser(int id);

    int getIdByneme(String name);

    Map<String, Object> register(String username, String password) throws UnsupportedEncodingException;


    String addLoginTicket(int userId);


    Map<String, Object> login(String username, String password);

    void logout(String ticket);
}
