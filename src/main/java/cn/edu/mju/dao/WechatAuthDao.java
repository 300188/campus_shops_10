package cn.edu.mju.dao;

import cn.edu.mju.entity.WechatAuth;
import com.fasterxml.jackson.databind.ser.Serializers;

public interface WechatAuthDao extends BaseDao {


    /**
     * 通过openId查询对应本平台的微信账号
     * @param openId
     * @return
     */
    WechatAuth queryWechatInfoByOpenId(String openId);


    /**
     * 添加对应本平台的微信账号
     * @param wechatAuth
     * @return
     */
    int insertWechatAuth(WechatAuth wechatAuth);



}
