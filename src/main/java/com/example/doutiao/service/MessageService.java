package com.example.doutiao.service;

import com.example.doutiao.bean.Message;
import com.example.doutiao.mapper.MessageDAO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wz on 2019/7/3.
 */
public interface MessageService {


    int addMessage(Message message);

    List<Message> getConversationList(int userId, int offset, int limit);

    List<Message> getConversationDetail(String conversationId, int offset, int limit);

    int getUnreadCount(int userId, String conversationId);


}
