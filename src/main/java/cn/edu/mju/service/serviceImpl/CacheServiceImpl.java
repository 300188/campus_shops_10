package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.cache.JedisUtil;
import cn.edu.mju.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CacheServiceImpl extends BaseServiceImpl implements CacheService {

    @Autowired
    private JedisUtil.Keys jedisKeys;


    @Override
    public void removeFromCache(String key) {

        Set<String> keySet = jedisKeys.keys(key + "*");
        for (String k:keySet
             ) {
            jedisKeys.del(key);
        }

    }


}
