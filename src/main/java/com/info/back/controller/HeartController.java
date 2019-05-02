package com.info.back.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 心跳检测
 */
@RestController
public class HeartController {
    @RequestMapping("heartBeat")
    public String heartBeat(){
        return "i'm ok!";
    }
}
