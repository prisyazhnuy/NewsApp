package com.prisyazhnuy.newsapp;

import android.app.Application;

//import com.splunk.mint.Mint;

/**
 * max.pr on 04.03.2018.
 */

public class NewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        Mint.initAndStartSession(this, "dc50571a");
    }
}
