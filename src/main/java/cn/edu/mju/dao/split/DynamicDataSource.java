package cn.edu.mju.dao.split;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/*
* Mysql主从读写分离实现
* */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {

        return DynamicDataSourceHolder.getDBType();
    }


}
