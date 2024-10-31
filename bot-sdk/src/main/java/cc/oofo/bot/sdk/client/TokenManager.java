package cc.oofo.bot.sdk.client;

import cc.oofo.bot.sdk.config.AppConfig;
import cn.hutool.core.convert.Convert;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 客户端 token 管理器
 *
 * @author Sir丶雨轩
 * @since 2024/10/31
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class TokenManager {

    private final AppConfig appConfig;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String TOKEN_PREFIX = "access_token";

    /**
     * 获取 token
     *
     * @return token
     */
    public String getToken() {
        Object token = redisTemplate.opsForValue().get(TOKEN_PREFIX);
        if (token == null) {
            TokenResult result = fetchNewToken();
            if (result != null) {
                token = result.token();
                // 将 token 写入 Redis
                redisTemplate.opsForValue().set(TOKEN_PREFIX, token, result.expiresIn(), TimeUnit.SECONDS);
            }
        }
        return Convert.toStr(token);
    }

    /**
     * 模拟获取新 token 的方法
     *
     * @return 新 token
     */
    private TokenResult fetchNewToken() {
        HttpRequest request = HttpUtil.createPost(appConfig.getOpenApiPath() + "/getAppAccessToken");
        JSONObject data = JSONUtil.createObj();
        data.set("appId", appConfig.getBot().getId());
        data.set("clientSecret", appConfig.getBot().getSecret());
        String body = request.body(data.toString()).execute().body();
        JSONObject entries = JSONUtil.parseObj(body);
        if (entries.containsKey("code")) {
            log.error("获取 token 失败: {}", body);
            return null;
        }
        log.info("获取 token 成功, Token:{} , 过期时间:{}", entries.getStr("access_token"), entries.getLong("expires_in"));
        return new TokenResult(entries.getStr("access_token"), entries.getLong("expires_in"));
    }

    /**
     * TokenResult 类，用于存储 token 和 expires_in
     */
    private record TokenResult(String token, long expiresIn) {
    }
}
