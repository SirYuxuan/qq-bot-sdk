package cc.oofo.bot.sdk.client.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 发送消息类型
 * @author Sir丶雨轩
 * @since 2024/10/31
 */
@Getter
@AllArgsConstructor
public enum ReqMessageType {

    TEXT(0),MARKDOWN(2),ARK(3),EMBED(4),MEDIA(7);
    /**
     * 消息类型值
     */
    final int val;
}
