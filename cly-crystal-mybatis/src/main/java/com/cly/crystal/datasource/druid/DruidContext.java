package com.cly.crystal.datasource.druid;

public class DruidContext {

    public void init() {
        System.setProperty("druid.logType", "slf4j");
    }

}
