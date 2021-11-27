package cn.hwali.hr.controller;

import cn.hwali.hr.model.ChatMsg;
import cn.hwali.hr.model.Hr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * @author lihua
 * @create 2021-05-18 8:09
 * 消息处理类
 */
@Controller
public class WsController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    /**
     * @param authentication 当前登录的用户，消息是从哪儿发来的,principal只包含name,不包含其他的
     * @param chatMsg        发送的消息
     */
    @MessageMapping("/ws/chat")
    public void handMsg(Authentication authentication, ChatMsg chatMsg) {
        Hr hr  = (Hr) authentication.getPrincipal();
        chatMsg.setFrom(hr.getUsername());
        chatMsg.setFromNickname(hr.getName());
        chatMsg.setDate(new Date());
        //监听前端队列"/queue/chat"里的内容
        simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(), "/queue/chat", chatMsg);
    }

    @MessageMapping("/ws/nf")
    @SendTo("/topic/nf")
    public String handleNF() {
        return "系统消息";
    }
}
