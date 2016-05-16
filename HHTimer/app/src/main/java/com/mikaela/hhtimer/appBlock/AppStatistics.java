package com.mikaela.hhtimer.appBlock;

/**
 * Created by Rebecca on 16-04-26.
 */



public class AppStatistics {

    public String appName;
    public Long appTime;

    public AppStatistics(String app, Long time) {
        this.appName = app;
        this.appTime = time;
    }

    public Long getAppTime() {
        return appTime;
    }

    public String getAppName() {
        return appName;
    }
}
