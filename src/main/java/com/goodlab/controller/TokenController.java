package com.goodlab.controller;


import com.goodlab.common.BaseResponse;
import com.goodlab.model.dto.AccessToken;
import com.goodlab.utils.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RequestMapping
@RestController
public class TokenController {

    @GetMapping("/")
    public BaseResponse<AccessToken> a(){
        String token = JwtUtil.createAccessToken("admin", Collections.emptyList());
        return JwtUtil.verifyTokenByRSA(token);
    }

}
