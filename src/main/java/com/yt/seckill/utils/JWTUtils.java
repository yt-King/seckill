package com.yt.seckill.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author 应涛
 * @date 2022/1/18
 * @function：
 */
public class JWTUtils {
    /**
     * 过期时间 单位 毫秒 默认 6小时
     */
    @Value("${seckill.EXPIRE_TIME}")
    private static  long EXPIRE_TIME;


    /**
     * 校验token是否正确
     * @param token 密钥
     * @param password 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String password) {
            Algorithm algorithm = Algorithm.HMAC256(password);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
    }

    /**
     * 生成签名
     * @param username 用户名
     * @param password 用户的密码
     * @return 加密的token
     */
    public static String sign(String username, String password) {
            //设置过期时间
            Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
            //加密密码
            Algorithm algorithm = Algorithm.HMAC256(password);
            // 附带username信息
            return JWT.create()
                    .withClaim("username", username)
                    .withExpiresAt(date)
                    .sign(algorithm);
    }

    /**
     * 判断过期
     * @param token
     * @return
     */
    public static boolean isExpire(String token){
        DecodedJWT jwt = null;
            jwt = JWT.decode(token);
        return System.currentTimeMillis()>jwt.getExpiresAt().getTime();
    }
}