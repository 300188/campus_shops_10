package cn.edu.mju.entity;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * 奖品
 */
@Getter@Setter
public class Award {

    //主键
    private Long awardId;

    //奖品名
    private String awardName;

    //奖品描述
    private String awardDesc;

    //奖品图片地址
    private String awardImg;

    //需要多少积分去兑换
    private Integer point;

    //权重 越大越排前显示
    private Integer priority;

    private Long createTime;

    private Long lastEditTime;

    //可用状态 0 不可用 1 可用
    private Integer enableStatus;

    //属于哪个店铺
    private Long shopId;





}
