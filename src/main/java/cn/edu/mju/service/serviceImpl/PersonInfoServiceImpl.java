package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.dao.PersonInfoDao;
import cn.edu.mju.entity.PersonInfo;
import cn.edu.mju.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonInfoServiceImpl extends BaseServiceImpl implements PersonInfoService {

    @Autowired
    private PersonInfoDao personInfoDao;

    @Override
    public PersonInfo getPersonInfoById(Long userId) {

        return personInfoDao.queryPersonInfoById(userId);

    }
}
