package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.BaseTest;
import cn.edu.mju.service.AreaService;
import cn.edu.mju.service.CacheService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class AreaServiceImplTest extends BaseTest {

    @Autowired
    private AreaService areaService;

    @Autowired
    private CacheService cacheService;


    @Test
    public void getAreaList() {

        cacheService.removeFromCache(AreaService.AREA_LIST_KEY);
        areaService.getAreaList();

    }
}