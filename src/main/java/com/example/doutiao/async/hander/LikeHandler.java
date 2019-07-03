package com.example.doutiao.async.hander;

import com.example.doutiao.async.EventHandler;
import com.example.doutiao.async.EventModel;
import com.example.doutiao.async.EventType;
import com.example.doutiao.bean.Message;
import com.example.doutiao.bean.User;
import com.example.doutiao.service.MessageService;
import com.example.doutiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;




@Component
public class LikeHandler implements EventHandler {

    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;
    @Override
    public void doHandler(EventModel model) {
        //System.out.println("Liked");
        Message message = new Message();
        message.setFromId(3);
        message.setToId(model.getActorId());
        User user = userService.getUser(model.getActorId());
        message.setConversationId("3");
        message.setContent("用户"+user.getName()+"点赞了，hettp://127.0.0.1:8080/news/"+model.getEntityId());
        message.setCreatedDate(new Date());
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
