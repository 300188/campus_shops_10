package cn.edu.mju.dao;

import cn.edu.mju.entity.PersonInfo;

public interface PersonInfoDao extends BaseDao {

    /**
     * 通过用户id查询用户
     */

    PersonInfo queryPersonInfoById(Long userId);


    /**
     * 添加用户信息
     * @param personInfo
     * @return
     */
    int insertPersonInfo(PersonInfo personInfo);



}
