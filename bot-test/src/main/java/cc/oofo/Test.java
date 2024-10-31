package cc.oofo;


import cc.oofo.bot.sdk.websocket.BotWebSocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class Test {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext run = SpringApplication.run(Test.class, args);
        BotWebSocket ws = run.getBean(BotWebSocket.class);
    }
}
