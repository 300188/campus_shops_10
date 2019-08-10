package cn.edu.mju.service;

import cn.edu.mju.BaseTest;
import cn.edu.mju.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;


public class AreaServiceTest extends BaseTest {

    @Autowired
    private AreaService areaService;

    @Test
    public void getAreaList() {

        List<Area> areaList = areaService.getAreaList();

        assertEquals(4,areaList.size());

    }


    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }

}