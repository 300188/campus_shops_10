package cn.edu.mju.entity;

/*
* 本地账号
* */

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class LocalAuth {

    private Long localAuthId;

    private String username;

    private String password;

    private Long createTime;

    private Long lastEditTime;

    private PersonInfo personInfo;


}
