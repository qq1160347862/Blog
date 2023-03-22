package com.example.blog.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.xml.crypto.Data;
import java.util.Date;

public class JwtUtils {

    //7天过期
    private static long expire = 604800;
    //8位秘钥
    //    原因可能是：根据传入的用户信息生成token时，使用的加密算法有点问题。
    //    秘钥必须使用HS开头的加密算法，可以使用的加密算法为HS256，HS384或HS512，他们的密钥字节数组必须分别为256位（32字节），384位（48字节）或512位（64字节）

    private static String secret = "yyxsblogyyxsblogyyxsblogyyxsblog";

    //生成Token
    public static String generateToken(String username){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * expire);
        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    //解析Token
    public static Claims getClaimsByToken(String token){
        return  Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
