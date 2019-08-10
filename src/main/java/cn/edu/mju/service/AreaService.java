package cn.edu.mju.service;

import cn.edu.mju.entity.Area;

import java.util.List;

public interface AreaService extends BaseService {

    public static final String AREA_LIST_KEY = "arealist";

    /*
    * 获取所有区域
    * */
    List<Area> getAreaList();

}
