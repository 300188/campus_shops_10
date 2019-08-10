package cn.edu.mju.dao.split;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Properties;

/*
* mybatis 拦截器拦截来分离读写
* */

/*
@Intercepts({@Signature(type=Executor.class,method = "update",args = {MappedStatement.class,Object.class}),
                @Signature(type = Executor.class,method = "query",args = {MappedStatement.class,Object.class,RowBounds.class,ResultHandler.class})})
*/
public class DynamicDataSourceInterceptor implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);

    private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        String lookupKey = DynamicDataSourceHolder.DB_MASTER;

        boolean synchronizationActive  = TransactionSynchronizationManager.isSynchronizationActive();
        if(synchronizationActive != true){
            Object[] objects = invocation.getArgs();
            MappedStatement ms = (MappedStatement)objects[0];

            //读方法
            if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {

                if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {

                    lookupKey = DynamicDataSourceHolder.DB_MASTER;


                }else{
                    BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
                    String sql  = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]"," ");
                    if(sql.matches(REGEX)){
                        lookupKey = DynamicDataSourceHolder.DB_MASTER;
                    }else{
                        lookupKey = DynamicDataSourceHolder.DB_SLAVE;
                    }

                }

            }

        }else{
            lookupKey = DynamicDataSourceHolder.DB_MASTER;
        }

        logger.debug("设置DynamicDataSourceHolderDBType=" + lookupKey);

        DynamicDataSourceHolder.setDbType(lookupKey);

        return invocation.proceed();
    }

    /*
    * 返回代理对象
    * */
    @Override
    public Object plugin(Object target) {

        if(target instanceof Executor){
            return Plugin.wrap(target,this);
        }else{
            return target;
        }
    }

    /*
    * 类初始化时调用
    * */
    @Override
    public void setProperties(Properties properties) {

    }
}
