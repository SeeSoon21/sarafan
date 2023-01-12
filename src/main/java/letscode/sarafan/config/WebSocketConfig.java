package letscode.sarafan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //судя по всему брокер сообщений располагается по этому адресу...но дальше то -- что?
        config.enableSimpleBroker("/topic");
        // предзначен для методов с аннотаций app
        // /ChangeMessage -- messageMapping на серверной стороне
        //(просто по сути для селекции)
        config.setApplicationDestinationPrefixes("/app");
    }

    //регистрация конечной точки STOMP
    //Эта конечная точка используется клиентами для подключения к STOMP-серверу
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //именно по этому урлу установлен протокол tcp, то есть, это ===
        // ws=new WS('https://localhost:8080/gs-guide-websocket')
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }
}
