package cn.edu.mju.service;

import cn.edu.mju.entity.PersonInfo;

/**
 * @see cn.edu.mju.service.serviceImpl.PersonInfoServiceImpl
 */

public interface PersonInfoService extends BaseService {


    PersonInfo getPersonInfoById(Long userId);


}
