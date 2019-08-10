package cn.edu.mju.entity;

import lombok.Getter;
import lombok.Setter;

/*
* 微信号实体类
* */
@Getter@Setter
public class WechatAuth {

    private Long wechatAuthId;

    //微信的openid
    private String openid;

    private Long createTime;

    private PersonInfo personInfo;

}
