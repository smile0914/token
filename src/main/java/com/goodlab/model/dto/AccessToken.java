package com.goodlab.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class AccessToken {
    //jwt所面向的用户
    private String sub;
    //jwt的签发时间
    private Long iat;
    //jwt的过期时间，必须要大于签发时间
    private Long exp;
    //jwt的唯一身份表示，主要用来作为一次性token，从而回避重放攻击
    private String jti;
    //用户名
    private String username;
    //用户权限
    private List<String> authorities;
}