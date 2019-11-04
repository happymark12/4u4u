package _4u4u_init.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import _4u4u.socket.MyHandler;
import _4u4u.socket.WebSocketInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
   
		@Autowired
	   private MyHandler myHandler;
		
		@Autowired
		private WebSocketInterceptor myInterceptor;

	
	@Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler, "/webSocket/{INFO}").setAllowedOrigins("*")
                .addInterceptors(myInterceptor);
    }
}
