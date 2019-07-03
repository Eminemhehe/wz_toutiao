package com.example.doutiao.bean;

import org.springframework.stereotype.Service;

/**
 * Created by wz on 2019/5/15.
 */

//单次访问用户，存储用户信息
@Service
public class HostHolder {

/*    本地线程变变量，set出一条线程，当get时，也是get，set出的这条线程。线程局部变量
    常见于web中储存当前用户到一个静态工具类，再线程的任何地方都可以访问到当前线程的用户。*/
    private static  ThreadLocal<User>  users = new ThreadLocal<User>();

    public User getUser(){
        return users.get();
    }
    public void setUser(User user){
         users.set(user);
    }
    public  void  clear(){
        users.remove();
    }

}
