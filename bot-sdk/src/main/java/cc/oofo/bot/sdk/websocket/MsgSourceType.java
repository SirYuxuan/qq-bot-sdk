package cc.oofo.bot.sdk.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Sir丶雨轩
 * @since 2024/10/31
 */
@Getter
@AllArgsConstructor
public enum MsgSourceType {

    /**
     * 来自好友
     */
    FRIEND("C2C_MESSAGE_CREATE"),

    /**
     * 来自群
     */
    GROUP("GROUP_AT_MESSAGE_CREATE");

    final String text;


}
