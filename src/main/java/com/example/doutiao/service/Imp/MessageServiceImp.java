package com.example.doutiao.service.Imp;


import com.example.doutiao.bean.Message;
import com.example.doutiao.mapper.MessageDAO;
import com.example.doutiao.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by nowcoder on 2016/7/7.
 */
@Service
public class MessageServiceImp implements MessageService {
    @Resource
    private MessageDAO messageDAO;

    public int addMessage(Message message) {
        return messageDAO.addMessage(message);
    }

    public List<Message> getConversationList(int userId, int offset, int limit) {
        // conversation的总条数存在id里
        return messageDAO.getConversationList(userId, offset, limit);
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        // conversation的总条数存在id里
        return messageDAO.getConversationDetail(conversationId, offset, limit);
    }

    public int getUnreadCount(int userId, String conversationId) {
        return messageDAO.getConversationUnReadCount(userId, conversationId);
    }
}
