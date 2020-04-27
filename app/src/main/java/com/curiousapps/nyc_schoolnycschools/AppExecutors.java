package com.curiousapps.nyc_schoolnycschools;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {

    private static AppExecutors inStance;

    public static AppExecutors get(){
        if (inStance == null){
        inStance = new AppExecutors();
        }
        return inStance;
    }

    private final ScheduledExecutorService mNetworkIo = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService networkIo(){
        return mNetworkIo;
    }
}
