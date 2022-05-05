package cn.bngel.redis.token;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.Map;

public interface TokenClient {

    /**
     * 设置token
     * @param key token-key 标识token的所有者
     * @param data 需要作为token中数据的字符串
     * @return token
     */
    String refreshToken(String key, String data);

    /**
     * 删除token
     * @param key 被删除的token的key
     */
    void delToken(String key);

    /**
     * 判断token是否合法
     * @param token token
     * @return 合法返回 true, 不合法则返回false
     */
    boolean verifyToken(String token);

    /**
     * 判断token是否合法
     * @param token token
     * @param json 额外校验的部分
     * @return 合法返回 true, 不合法则返回false
     */
    boolean verifyToken(String token, JSONObject json);
}
