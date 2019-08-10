package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.dao.PersonInfoDao;
import cn.edu.mju.dao.WechatAuthDao;
import cn.edu.mju.dto.WechatAuthExecution;
import cn.edu.mju.entity.PersonInfo;
import cn.edu.mju.entity.WechatAuth;
import cn.edu.mju.enums.WechatAuthStateEnum;
import cn.edu.mju.service.WechatAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WechatAuthServiceImpl extends BaseServiceImpl implements WechatAuthService {

    @Autowired
    private WechatAuthDao wechatAuthDao;

    @Autowired
    private PersonInfoDao personInfoDao;


    @Override
    public WechatAuth getWechatAuthByOpenId(String openId) {
        return wechatAuthDao.queryWechatInfoByOpenId(openId);
    }

    @Transactional
    @Override
    public WechatAuthExecution register(WechatAuth wechatAuth) throws RuntimeException {
        if (wechatAuth == null || wechatAuth.getOpenid() == null) {
            return new WechatAuthExecution(WechatAuthStateEnum.NULL_AUTH_INFO);
        }
        try {
            wechatAuth.setCreateTime(System.currentTimeMillis());
                try {
                    wechatAuth.getPersonInfo().setCreateTime(System.currentTimeMillis());
                    wechatAuth.getPersonInfo().setLastEditTime(System.currentTimeMillis());
                    wechatAuth.getPersonInfo().setEnableStatus(1);
                    PersonInfo personInfo = wechatAuth.getPersonInfo();
                    int effectedNum = personInfoDao
                            .insertPersonInfo(personInfo);
                    wechatAuth.setPersonInfo(personInfo);
                    if (effectedNum <= 0) {
                        throw new RuntimeException("添加用户信息失败");
                    }
                } catch (Exception e) {
                    throw new RuntimeException("insertPersonInfo error: "
                            + e.getMessage());
                }

            int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
            if (effectedNum <= 0) {
                throw new RuntimeException("帐号创建失败");
            } else {
                return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS,
                        wechatAuth);
            }
        } catch (Exception e) {
            throw new RuntimeException("insertWechatAuth error: "
                    + e.getMessage());
        }
    }
}
