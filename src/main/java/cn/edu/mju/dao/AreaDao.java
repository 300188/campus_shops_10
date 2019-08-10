package cn.edu.mju.dao;

import cn.edu.mju.entity.Area;

import java.util.List;

public interface AreaDao extends BaseDao {

    /*
    * 列出区域列表
    * */
    List<Area> queryAreas();


}
