package cn.edu.mju.util;


/*
* 通过页码来计算数据库行数
* */
public class PageCalculator {


    public static int calculateRowIndex(int pageIndex,int pageSize) {

        return (pageIndex > 0 ? (pageIndex - 1) * pageSize : 0);

    }


}
