package cn.edu.mju.dao;

import cn.edu.mju.BaseTest;
import cn.edu.mju.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;


public class AreaDaoTest extends BaseTest {

    @Autowired
    private AreaDao areaDao;

    @Test
    public void queryAreas() {

        List<Area> areaList = areaDao.queryAreas();

        assertEquals(4,areaList.size());

    }
}