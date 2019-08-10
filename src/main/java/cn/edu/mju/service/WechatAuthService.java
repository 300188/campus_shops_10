package cn.edu.mju.service;

import cn.edu.mju.dto.WechatAuthExecution;
import cn.edu.mju.entity.WechatAuth;

/**
 * 微信登录
 * @see cn.edu.mju.service.serviceImpl.WechatAuthServiceImpl
 */
public interface WechatAuthService extends BaseService {


    /**
     * 通过openId查找平台对应的微信账号
     *
     * @param openId
     * @return
     */
    WechatAuth getWechatAuthByOpenId(String openId);


    /**
     * 注册本平台的微信账号
     * @param wechatAuth
     * @return
     */
    WechatAuthExecution register(WechatAuth wechatAuth) throws RuntimeException;



}
