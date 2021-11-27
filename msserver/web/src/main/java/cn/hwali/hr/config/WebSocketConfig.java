package cn.hwali.hr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author lihua
 * @create 2021-05-18 8:06
 */
@Configuration
//通过EnableWebSocketMessageBroker 开启使用STOMP协议来传输基于代理(message broker)的消息,此时浏览器支持使用@MessageMapping 就像支持@RequestMapping一样。
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     *
      * 注册stomp的端点
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //前端连接的点 用ws前缀，跟http请求区分开 setAllowedOrigins("*")：允许所有的域发送webSocket请求
        registry.addEndpoint("/ws/ep")
//                .setAllowedOrigins("http://localhost:8080")
                 .setAllowedOriginPatterns("*")
//                .setAllowedOrigins("*")
                .withSockJS();
    }

    /**
     *
     *配置消息代理(message broker)
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //订阅Broker名称
        registry.enableSimpleBroker("/queue","/topic");
    }
}
