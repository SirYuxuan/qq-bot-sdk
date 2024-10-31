package cc.oofo.bot.sdk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    /**
     * 是否为沙盒环境
     */
    private boolean sandbox;

    /**
     * 正式 API 路径
     */
    private String apiPath;

    /**
     * 沙盒 API 路径
     */
    private String apiPathSandbox;

    /**
     * 开放 API 路径
     */
    private String openApiPath;

    /**
     * 机器人相关配置
     */
    private BotConfig bot;

    @Data
    public static class BotConfig {
        /**
         * 机器人 ID
         */
        private String id;

        /**
         * 机器人 QQ 号
         */
        private String qq;

        /**
         * 机器人令牌
         */
        private String token;

        /**
         * 机器人密钥
         */
        private String secret;
    }

    /**
     * 获取 API 路径
     *
     * @return API 路径
     */
    public String getApiPath() {
        return sandbox ? apiPathSandbox : apiPath;
    }
}
