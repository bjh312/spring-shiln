package com.shiln.order.config;

import com.shiln.order.config.BaseItem.Action;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

/**
 * Created by baojunhu on 2017/3/28.
 */
@Intercepts({@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
)})
public class ActionInterceptor implements Interceptor {
    private static final String BASIC_ISO_DATE_TIME = "yyyyMMddHHmmss";

    public ActionInterceptor() {
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement ms = (MappedStatement)invocation.getArgs()[0];
        Object param = invocation.getArgs()[1];
        if(ms == null) {
            return invocation.proceed();
        } else {
            long now;
            BaseItem e;
            if(ms.getSqlCommandType().equals(SqlCommandType.INSERT) && param != null && param instanceof BaseItem) {
                now = this.currentDateTime();
                e = (BaseItem)param;
                e.setCreateTime(Long.valueOf(now));
                e.setActionTime(Long.valueOf(now));
                e.setAction(Action.INSERT.getValue());
            } else if(ms.getSqlCommandType().equals(SqlCommandType.UPDATE) && param != null && param instanceof BaseItem) {
                now = this.currentDateTime();
                e = (BaseItem)param;
                e.setActionTime(Long.valueOf(now));
                e.setAction(Action.UPDATE.getValue());
            }

            return invocation.proceed();
        }
    }

    @Override
    public Object plugin(Object target) {
        return target instanceof Executor?Plugin.wrap(target, this):target;
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private long currentDateTime() {
        return Long.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))).longValue();
    }
}

