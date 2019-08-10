package cn.edu.mju.service;

import cn.edu.mju.dto.LocalAuthExecution;
import cn.edu.mju.entity.LocalAuth;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface LocalAuthService extends BaseService {

    /**
     *
     * 根据用户名和密码获取用户信息
     */
    LocalAuth getLocalAuthByUserNameAndPwd(String username, String password);

    /**
     * 根据用户id获取用户信息
     */
    LocalAuth getLocalAuthByUserId(long userId);

    /**
     *
     *
     *注册用户账号
     * @param localAuth
     * @param profileImg
     * @return
     * @throws RuntimeException
     */
    LocalAuthExecution register(LocalAuth localAuth,
                                CommonsMultipartFile profileImg) throws RuntimeException;

    /**
     *
     *绑定微信账号
     * @param localAuth
     * @return
     * @throws RuntimeException
     */
    LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
            throws RuntimeException;

    /**
     *修改密码
     */
    LocalAuthExecution modifyLocalAuth(Long userId, String username,
                                       String password, String newPassword);


}
