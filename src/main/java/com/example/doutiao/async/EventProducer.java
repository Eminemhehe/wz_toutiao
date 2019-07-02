package com.example.doutiao.async;

import com.alibaba.fastjson.JSONObject;
import com.example.doutiao.util.JedisAdapter;
import com.example.doutiao.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wz on 2019/5/17.
 */
//发送Eventtype发生时产生的EventModel的所有数据，把数据序列化放入redis队列
@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel model) {

        try {
            String json = JSONObject.toJSONString(model);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
