package cn.edu.mju.service;

import cn.edu.mju.entity.HeadLine;

import java.util.List;

public interface HeadLineService extends BaseService {


    //jedis中存放的key
    public static final String HEAD_LINE_LIST_KEY = "headlinelist";

    /*
    * 查询所有头条
    * */
    List<HeadLine> getHeadLineList(HeadLine headLine);

    /*
    * 根据头条id查询头条
    * */
    HeadLine getHeadLineById(Long lineId);

    /*
    * 插入一条头条信息
    * */





}
