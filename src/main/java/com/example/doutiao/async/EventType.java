package com.example.doutiao.async;

/**
 * Created by wz on 2019/5/17.
 */

//枚举 刚刚发生的事件
public enum EventType {
    LIKE(0),
    COMMIT(1),
    LOGIN(2),
    MAIL(3);

    private  int  value;
    EventType(int value){
        this.value=value;
    }
    public int getValue(){
        return  value;
    }
}
