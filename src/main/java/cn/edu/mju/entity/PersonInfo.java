package cn.edu.mju.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
* 用户信息实体类
* */
@Getter@Setter@ToString
public class PersonInfo {

    private Long userId;

    private String name;

    //头像地址
    private String image;

    private String email;

    private String gender;

    //状态
    private Integer enableStatus;

    //用户类型 1 顾客 2 店家 3 超级管理员
    private Integer userType;

    private Long createTime;

    private Long lastEditTime;


}
