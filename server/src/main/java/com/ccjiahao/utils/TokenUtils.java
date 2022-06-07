package com.ccjiahao.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.ccjiahao.entity.User;

import java.util.Date;

public class TokenUtils {
    private static final String SECRET = "Axmk89Li3Aji9M";

    public static String createToken(String username, boolean remember) {
        String token="";
        int days = remember ? 15 : 1;
        Date date = new Date(System.currentTimeMillis() + days * 24 * 60 * 60 * 1000);
        token= JWT.create().withExpiresAt(date).withSubject(username).sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    public static boolean verifyToken(String token){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            verifier.verify(token);
            return true;
        } catch(Exception e){
            throw new TokenExpiredException("token失效");
        }
    }

    public static String getUserByToken(String token) throws Exception {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token).getSubject();
        } catch (TokenExpiredException e){
            throw new Exception("token已失效，请重新登录", e);
        } catch (JWTVerificationException e) {
            throw new Exception("token验证失败！", e);
        }
    }
}
