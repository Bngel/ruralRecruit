package cn.bngel.redis.token;

import cn.bngel.pojo.Constant;
import cn.bngel.redis.SimpleRedisClient;
import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class TokenRedisClient implements TokenClient{

    @Override
    public String refreshToken(String key, String data) {
        // 将数据进行加密后得到实际的token存到redis中
        return (String) redisClient.sync(jedis -> {
            String token = jedis.get(Constant.CACHE_TOKEN + key);
            if (token == null) {
                token = getToken(data);
                redisClient.set(Constant.CACHE_TOKEN + key, token);
            }
            return token;
        });
    }

    @Override
    public void delToken(String key) {
        redisClient.del(key);
    }

    @Override
    public boolean verifyToken(String token) {
        String salt = redisClient.get(Constant.CACHE_TOKEN_SALT);
        if (token == null || salt == null) {
            return false;
        }
        String decryptedToken = decryptToken(token, salt);
        JSONObject jsonObject = JSONUtil.toBean(decryptedToken, JSONObject.class);
        String id = String.valueOf(jsonObject.get(Constant.TOKEN_PARAM_ID));
        String cachedToken = redisClient.get(Constant.CACHE_TOKEN + id);
        return token.equals(cachedToken);
    }

    @Override
    public boolean verifyToken(String token, JSONObject json) {
        if (token == null) {
            return false;
        }
        String decryptedToken = getDecryptedToken(token);
        JSONObject jsonObject = JSONUtil.toBean(decryptedToken, JSONObject.class);
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());
            if (!jsonObject.containsKey(key)) {
                return false;
            }
            String valueForCheck = String.valueOf(jsonObject.get(key));
            if (!value.equals(valueForCheck)) {
                return false;
            }
        }
        String id = (String) jsonObject.get(Constant.TOKEN_PARAM_ID);
        String cachedToken = redisClient.get(Constant.CACHE_TOKEN + id);
        return token.equals(cachedToken);
    }

    @Override
    public String getDecryptedToken(String token) {
        String salt = redisClient.get(Constant.CACHE_TOKEN_SALT);
        return decryptToken(token, salt);
    }

    @Autowired
    private SimpleRedisClient redisClient;

    /**
     * 得到进行加密的盐, 有效期为24小时(默认)
     * @return 盐
     */
    private String createSalt() {
        byte[] encoded = SecureUtil.generateKey(SymmetricAlgorithm.DESede.getValue()).getEncoded();
        return Base64.encode(encoded);
    }

    /**
     * 将数据转换为token串
     * @param data 数据字符串(json)
     * @return token
     */
    private String getToken(String data) {
        String salt = redisClient.get(Constant.CACHE_TOKEN_SALT);
        if (salt == null) {
            salt = createSalt();
            redisClient.setex(Constant.CACHE_TOKEN_SALT, Constant.CACHE_EXPIRE_TOKEN, salt);
        }
        return encryptToken(data, salt);
    }

    /**
     * 对数据进行加密得到token串
     * @param originToken 需要进行加密的数据
     * @param salt 加盐处理
     * @return 加密后实际使用的token
     */
    private String encryptToken(String originToken, String salt) {
        byte[] decodedKey = Base64.decode(salt);
        DES des = SecureUtil.des(decodedKey);
        return des.encryptHex(originToken);
    }

    /**
     * 对加密的token进行解密处理
     * @param encryptedToken 需要解密的token串
     * @param salt 盐
     * @return 解密后的数据字符串
     */
    private String decryptToken(String encryptedToken, String salt) {
        byte[] decodedKey = Base64.decode(salt);
        DES des = SecureUtil.des(decodedKey);
        return des.decryptStr(encryptedToken);
    }
}
