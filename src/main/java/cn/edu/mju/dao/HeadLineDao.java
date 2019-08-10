package cn.edu.mju.dao;

/*
* 头条
* */

import cn.edu.mju.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeadLineDao extends BaseDao {


    /*
    * 根据传入的查询条件
    *
    * */
    List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);


    /*
    * 根据头条id查询一条头条信息
    * */

    HeadLine queryHeadLineById(Long lineId);


    /*
    *
    * 插入一条头条信息
    * */

    int insertHeadLine(HeadLine headLine);




}
