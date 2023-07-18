package com.goodlab.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.goodlab.common.BaseResponse;
import com.goodlab.common.ErrorCode;
import com.goodlab.common.ResultUtils;
import com.goodlab.model.dto.AccessToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

/**
 * JWT工具类
 */
public class JwtUtil {

    //有效期为 1个小时
    public static final Integer JWT_TTL = 60 * 60;
    //设置秘钥明文
    public static final String JWT_KEY = "goodlab";
    //设置签证算法
    public static final SecretKey keys = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * 返回载荷信息
     * @param token token
     * @return 载荷信息
     */
    public static BaseResponse verifyTokenByRSA(String token) {
        AccessToken payloadDto = AccessToken.builder().build();
        Claims claims = Jwts.parser()
                .setSigningKey(keys)
                .parseClaimsJws(token)
                .getBody();
        BeanUtil.copyProperties(claims,payloadDto);
        if (payloadDto.getExp() < new Date().getTime()) {
            return ResultUtils.error(ErrorCode.NULL_ERROR,"token已过期");
        }
        return ResultUtils.success(payloadDto);
    }

    /**
     * 生成token
     * @param userName 用户名
     * @param authorities 用户权限
     * @return token
     */
    public static String createAccessToken(String userName, List<String> authorities) {
        Date now = new Date();
        Date exp = DateUtil.offsetSecond(now, JWT_TTL);  //一个小时过期
        //设置载荷
        AccessToken payloadDto = AccessToken.builder()
                .sub(JWT_KEY)
                .iat(now.getTime())
                .exp(exp.getTime())
                .jti(UUID.randomUUID().toString())
                .username(userName)
                .authorities(authorities)
                .build();
        //创建建造者对象
        JwtBuilder jwts = Jwts.builder()
                .setPayload(JSONUtil.toJsonStr(payloadDto)) //设置载荷
                .setHeaderParam("alg", "HS256") // 设置头
                .setHeaderParam("typ", "JWT")
                .signWith(keys);  //设置签证
        //生成token
        return jwts.compact();
    }
}
