package cn.edu.mju.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 顾客已领取的奖品映射
 */
@Getter@Setter
public class UserAwardMap {

    private Long userAwardId;

    private Long createTime;

    //使用状态 0 未兑换 1已兑换
    private Integer usedStatus;

    //领取奖品所消耗的积分
    private Integer point;

    //顾客信息实体类
    private PersonInfo user;

    //奖品信息
    private Award award;

    //操作员信息实体类
    private PersonInfo operator;



}
