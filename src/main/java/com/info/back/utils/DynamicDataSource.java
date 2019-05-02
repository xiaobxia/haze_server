package com.info.back.utils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource{
    public static final String QBM_SERVER = "haze_server";

    public static final String QBM_CS = "haze_cs";
    //本地线程，获取当前正在执行的currentThread
    public static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setCustomerType(String customerType) {

        contextHolder.set(customerType);

    }

    public static String getCustomerType() {
        return contextHolder.get();

    }

    public static void clearCustomerType() {

        contextHolder.remove();

    }

    @Override
    protected Object determineCurrentLookupKey() {

        return getCustomerType();

    }

}
