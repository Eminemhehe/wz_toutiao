package com.example.doutiao.async;

import java.util.List;

/**
 * Created by wz on 2019/5/17.
 */
public interface EventHandler {
    void doHandler(EventModel model);
    List<EventType> getSupportEventTypes();//每个handler所支持的type事务
}
