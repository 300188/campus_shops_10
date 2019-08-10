package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.dao.LocalAuthDao;
import cn.edu.mju.dto.LocalAuthExecution;
import cn.edu.mju.entity.LocalAuth;
import cn.edu.mju.enums.LocalAuthStateEnum;
import cn.edu.mju.exceptions.LoaclAuthOperationException;
import cn.edu.mju.service.LocalAuthService;
import cn.edu.mju.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


@Service
public class LocalAuthServiceImpl extends BaseServiceImpl implements LocalAuthService {


    @Autowired
    private LocalAuthDao localAuthDao;


    @Override
    public LocalAuth getLocalAuthByUserNameAndPwd(String username, String password) {
        return localAuthDao.queryLocalByUserNameAndPwd(username,MD5.getMd5(password));
    }

    @Override
    public LocalAuth getLocalAuthByUserId(long userId) {
        return localAuthDao.queryLocalByUserId(userId);
    }

    @Override
    @Transactional
    public LocalAuthExecution register(LocalAuth localAuth, CommonsMultipartFile profileImg) throws RuntimeException {
        return null;
    }

    @Override
    @Transactional
    public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws RuntimeException {

        if (localAuth == null || localAuth.getPassword() == null
                || localAuth.getUsername() == null
                || localAuth.getPersonInfo() ==null || localAuth.getPersonInfo().getUserId() == null) {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
        //判断是否已经绑定过了
        LocalAuth tempAuth = localAuthDao.queryLocalByUserId(localAuth
                .getPersonInfo().getUserId());
        if (tempAuth != null) {
            return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
        }
        try {
            localAuth.setCreateTime(System.currentTimeMillis());
            localAuth.setLastEditTime(System.currentTimeMillis());
            localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
            int effectedNum = localAuthDao.insertLocalAuth(localAuth);
            if (effectedNum <= 0) {
                throw new LoaclAuthOperationException("帐号绑定失败");
            } else {
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS,
                        localAuth);
            }
        } catch (Exception e) {
            throw new LoaclAuthOperationException("insertLocalAuth error: "
                    + e.getMessage());
        }
    }

    @Override
    @Transactional
    public LocalAuthExecution modifyLocalAuth(Long userId, String username, String password, String newPassword) {
        if (userId != null && username != null && password != null
                && newPassword != null && !password.equals(newPassword)) {
            try {
                int effectedNum = localAuthDao.updateLocalAuth(userId,
                        username, MD5.getMd5(password),
                        MD5.getMd5(newPassword), System.currentTimeMillis());
                if (effectedNum <= 0) {
                    throw new LoaclAuthOperationException("更新密码失败");
                }
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
            } catch (Exception e) {
                throw new LoaclAuthOperationException("更新密码失败:" + e.toString());
            }
        } else {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
    }
}
