package cn.edu.mju.service;

/*
*对redis进行操作
* */
public interface CacheService extends BaseService {

    /*
    * 根据key删除对应的String
    * */

    void removeFromCache(String key);


}
