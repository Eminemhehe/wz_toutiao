/*
package com.example.doutiao;

import com.example.doutiao.mapper.LoginTicketDAO;
import com.example.doutiao.mapper.NewsDAO;
import com.example.doutiao.mapper.UserDAO;
import com.example.doutiao.bean.LoginTicket;
import com.example.doutiao.bean.News;
import com.example.doutiao.bean.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;


@RunWith(SpringRunner.class)
@SpringApplicationConfiguration(classes = DoutiaoApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {


    @Resource
    UserDAO userDAO;
    @Resource
    NewsDAO newsDAO;

    @Resource
    LoginTicketDAO loginTicketDAO;


    @Test
    public void contextLoads() {
        Random random = new Random();
        for (int i = 0; i < 11; i++) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d", i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);
            user.setPassword("newspassword");
            userDAO.updatePassword(user);


            News news = new News();
            news.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime()+1000*3600*5*i);
            news.setCreatedDate(date);
            news.setImage(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            news.setLikeCount(i+1);
            news.setUserId(i+1);
            news.setTitle(String.format("TITLE{%d}",i));
            news.setLink(String.format("http://www.nowcoder.com/%d.html",i));
            newsDAO.addNews(news);



            LoginTicket ticket = new LoginTicket();
            ticket.setStatus(0);
            ticket.setUserId(i+1);
            ticket.setExpired(date);
            ticket.setTicket(String.format("TICKET%d", i+1));
            loginTicketDAO.addTicket(ticket);
            loginTicketDAO.updateStatus(ticket.getTicket(), 2);


        }
        Assert.assertEquals("newspassword", userDAO.selectById(1).getPassword());
        userDAO.deleteById(1);
        Assert.assertNull(userDAO.selectById(1));


        //Assert.assertEquals("newpassword", userDAO.selectById(1).getPassword());
        userDAO.deleteById(1);
        Assert.assertNull(userDAO.selectById(1));

        Assert.assertEquals(1, loginTicketDAO.selectByTicket("TICKET1").getUserId());
        Assert.assertEquals(2, loginTicketDAO.selectByTicket("TICKET1").getStatus());


}

}
*/
