package com.example.doutiao.async;

import com.alibaba.fastjson.JSONObject;
import com.example.doutiao.util.JedisAdapter;
import com.example.doutiao.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wz on 2019/5/17.
 */

//将EventProduce队列中的Model取出来，反序列化，还原现场，找到对应的handler，处理事务
@Service
public class EventComsumer implements InitializingBean, ApplicationContextAware {

    @Resource
    JedisAdapter jedisAdapter;


    private static final Logger logger = LoggerFactory.getLogger(EventComsumer.class);
    private ApplicationContext applicationContext;
    private Map<EventType, List<EventHandler>> config = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //遍历所有Handler，找到所有实现接口的handler，看每一个hander想要注册的事件，登记到config（Map）中
    //config最终的key=事件，value=要处理此事件的所有handler
    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if (beans != null) {
            for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
                List<EventType> eventTypes = entry.getValue().getSupportEventTypes();
                for (EventType type : eventTypes) {
                    if (!config.containsKey(type)) {
                        config.put(type, new ArrayList<EventHandler>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String key = RedisKeyUtil.getEventQueueKey();
                    List<String> event = jedisAdapter.brpop(0, key);
                    for (String masssage : event) {
                        if (masssage.equals(key)) {
                            continue;
                        }
                        EventModel eventModel = JSONObject.parseObject(masssage, EventModel.class);
                        if (!config.containsKey(eventModel.getType())) {
                            logger.error("不能识别");
                            continue;
                        }
                        //识别那个handler处理事务
                        for (EventHandler handler : config.get(eventModel.getType())) {
                            handler.doHandler(eventModel);
                        }
                    }
                }
            }
        });
        thread.start();
    }


}

