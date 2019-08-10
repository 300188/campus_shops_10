package cn.edu.mju.dao;


import cn.edu.mju.entity.LocalAuth;
import org.apache.ibatis.annotations.Param;

/*
* 本地账号
* */
public interface LocalAuthDao extends BaseDao {


    /*
    * 通过本地账号和密码查询对应信息，登录
    * */
    LocalAuth queryLocalByUserNameAndPwd(@Param("username") String username , @Param("password") String password);


    /*
    * 通过用户id 查询对应的localauth
    * */

    LocalAuth queryLocalByUserId(@Param("userId") Long userId);


    /*
    * 添加平台账号
    * */
    int insertLocalAuth(LocalAuth localAuth);


    /*
    * 通过userId username password 更改密码
    * */
    int updateLocalAuth(@Param("userId") Long userId, @Param("username") String username
        ,@Param("password") String password,@Param("newPassword")String newPassword,
                        @Param("lastEditTime") Long lastEditTime);







}
