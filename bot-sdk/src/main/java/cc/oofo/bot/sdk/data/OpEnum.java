package cc.oofo.bot.sdk.data;

import lombok.Getter;

/**
 * @author Sir丶雨轩
 * @since 2024/10/31
 */
@Getter
public enum OpEnum {

    DISPATCH(0),
    HEARTBEAT(1),
    IDENTIFY(2),
    PRESENCE_UPDATE(3),
    VOICE_STATE_UPDATE(4),
    RESUME(6),
    RECONNECT(7),
    REQUEST_GUILD_MEMBERS(8),
    INVALID_SESSION(9),
    HELLO(10),
    HEARTBEAT_ACK(11),
    HTTP_CALLBACK_ACK(12);

    private final int code;

    OpEnum(int code) {
        this.code = code;
    }

    public static OpEnum fromCode(int code) {
        for (OpEnum value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        return null;
    }

}
