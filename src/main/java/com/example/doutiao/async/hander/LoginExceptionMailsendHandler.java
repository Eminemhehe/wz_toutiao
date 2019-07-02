package com.example.doutiao.async.hander;

import com.example.doutiao.async.EventHandler;
import com.example.doutiao.async.EventModel;
import com.example.doutiao.async.EventType;
import com.example.doutiao.model.Message;
import com.example.doutiao.service.MessageService;
import com.example.doutiao.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by nowcoder on 2016/7/16.
 */
@Component
public class LoginExceptionMailsendHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Resource
    MailSender mailSender;

    public void doHandler(EventModel model) {
        // 判断是否有异常登陆
        Message message = new Message();
        message.setToId(model.getActorId());
        message.setContent("你上次的登陆ip异常");
        message.setFromId(3);
        message.setConversationId("3");
        message.setCreatedDate(new Date());
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
