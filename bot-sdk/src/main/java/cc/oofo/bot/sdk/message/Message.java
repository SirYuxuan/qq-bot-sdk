package cc.oofo.bot.sdk.message;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Sir丶雨轩
 * @since 2024/10/31
 */
@Data
public class Message {
    /**
     * 消息发送者
     */
    private Author author;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息id
     */
    private String id;
    /**
     * 消息场景
     */
    private MessageScene messageScene;
    /**
     * 消息发送时间
     */
    private LocalDateTime timestamp;
    /**
     * 群id
     */
    private String groupId;
    /**
     * 群名称
     */
    private String groupOpenid;
    /**
     * 消息发送者
     */
    @Data
    public static class Author {
        /**
         * 发送者id
         */
        private String id;
        /**
         * 发送者在当前群内Id
         */
        private String memberOpenid;
        /**
         * 发送者在App下的Id
         */
        private String unionOpenid;
    }

    /**
     * 消息场景
     */
    @Data
    public static class MessageScene {
        private String source;
    }
}