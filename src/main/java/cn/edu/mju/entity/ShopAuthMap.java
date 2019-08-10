package cn.edu.mju.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 店铺授权
 */
@Getter@Setter
public class ShopAuthMap {

    private Long shopAuthId;

    //职称名
    private String title;

    //职称符号 可用于权限控制
    private Integer titleFlag;

    //授权有效状态 0无效 1 有效
    private Integer enableStatus;

    private Long createTime;

    //员工信息实体类
    private PersonInfo employee;

    //店铺实体类
    private Shop shop;



}
