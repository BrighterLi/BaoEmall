package com.xiaoming.baoemall;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class EmallApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
